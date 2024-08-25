package app.nchu.tsc.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.nchu.tsc.models.Member;
import app.nchu.tsc.models.Redirecting;
import app.nchu.tsc.repositories.RoleRepository;
import app.nchu.tsc.services.MemberService;
import app.nchu.tsc.services.RedirectingService;
import app.nchu.tsc.services.SystemVariableService;
import app.nchu.tsc.services.TSCSettings;
import app.nchu.tsc.utilities.CookieBuilder;
import app.nchu.tsc.utilities.Random;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthRestController {

    @Autowired
    private SystemVariableService systemVariableService;

    @Autowired
    private RedirectingService redirectingService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TSCSettings tscSettings;

    @GetMapping("/callback")
    private void callback(@RequestParam String state, @RequestParam String user_id, @RequestParam String res_token, HttpServletResponse response) {
        Redirecting r = redirectingService.getRedirecting(state);

        if (r == null || redirectingService.isExpired(r)) {
            response.setStatus(404);
            return;
        }

        UUID resID = UUID.fromString(user_id);
        Member member;
        if (memberService.isMemberExistsByResID(resID)) {
            member = memberService.getMemberByResID(resID);
        } else {
            member = memberService.createMember(
                Member.builder()
                    .resID(resID)
                    .resToken(res_token)
                    .token(Random.generateRandomString(128))
                    .role(roleRepository.findById(systemVariableService.get("default_role")).orElse(null))
                    .build()
            );
        }

        response.addCookie(
            (new CookieBuilder("member_id", member.getId().toString()))
                .httpOnly(false).secure(true).path("/").maxAge(2592000)
                .domain(tscSettings.getFrontendURL(false, false)).build()
        );

        response.addCookie(
            (new CookieBuilder("member_token", member.getToken()))
                .httpOnly(true).secure(true).path("/").maxAge(2592000)
                .domain(tscSettings.getFrontendURL(false, false)).build()
        );

        response.setHeader("Location", r.getHref());
        response.setStatus(302);
    }

}
