package com.authentication.controller.teamMemberController;




import com.authentication.dto.teamMemberDto.TeamMemberDetailsResponse;
import com.authentication.dto.teamMemberDto.TeamMemberRequest;
import com.authentication.dto.teamMemberDto.TeamMemberResponse;
import com.authentication.service.teamService.TeamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/companyServices/company/team/members")


public class TeamMemberController {

    @Autowired
    private TeamMemberService teamMemberService;

    @PostMapping("/addTeamMember")
    public ResponseEntity<TeamMemberResponse> createTeamMember(@RequestBody TeamMemberRequest teamMemberRequest,
                                                               @RequestParam Long companyId,@RequestParam Long createdById) {
        TeamMemberResponse createdTeamMember = teamMemberService.createTeamMember(teamMemberRequest,companyId,createdById);
        return new ResponseEntity<>(createdTeamMember, HttpStatus.CREATED);
    }

    @PutMapping("/updateTeamMember")
    public ResponseEntity<TeamMemberResponse> updateTeamMember(
            @RequestParam Long id,
            @RequestBody TeamMemberRequest teamMemberRequest) {
        TeamMemberResponse updatedTeamMember = teamMemberService.updateTeamMember(id, teamMemberRequest);
        return new ResponseEntity<>(updatedTeamMember, HttpStatus.OK);
    }


    @GetMapping("/getAllTeamMembers")
    public ResponseEntity<List<TeamMemberResponse>> getAllTeamMembers(@RequestParam Long companyId) {
        List<TeamMemberResponse> allTeamMembers = teamMemberService.getAllTeamMembers(companyId);
        return new ResponseEntity<>(allTeamMembers, HttpStatus.OK);
    }


    @GetMapping("/getTeamMember")
    public ResponseEntity<TeamMemberResponse> getTeamMemberById(@RequestParam Long id) {
        TeamMemberResponse teamMember = teamMemberService.getTeamMemberById(id);
        return new ResponseEntity<>(teamMember, HttpStatus.OK);
    }

    @DeleteMapping("/removeTeamMember")
    public ResponseEntity<Void>  removeTeamMember(@RequestParam Long memberId)

    {
        teamMemberService.removeTeamMember(memberId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/memberCompanyDetails")
    public ResponseEntity<TeamMemberDetailsResponse> getTeamMemberDetails(@RequestParam String memberMail) {
        TeamMemberDetailsResponse teamMemberDetailsResponse = teamMemberService.getTeamMemberDetailsByMail(memberMail);
        if (teamMemberDetailsResponse != null) {
            return new ResponseEntity<>(teamMemberDetailsResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAllTeamMembersWithIdAndTeamName")
    public ResponseEntity<List<HashMap<String, Object>>> getAllTeamMembersWithIdAndTeamMemberName(@RequestParam Long companyId) {
        List<HashMap<String, Object>> teamMembers = teamMemberService.getTeamMembersWithIdAndName(companyId);
        return ResponseEntity.ok(teamMembers);
    }




}
