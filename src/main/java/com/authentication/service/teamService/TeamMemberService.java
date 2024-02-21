package com.authentication.service.teamService;



import com.authentication.dto.teamMemberDto.TeamMemberDetailsResponse;
import com.authentication.dto.teamMemberDto.TeamMemberRequest;
import com.authentication.dto.teamMemberDto.TeamMemberResponse;
import java.util.HashMap;
import java.util.List;

public interface TeamMemberService {

    TeamMemberResponse createTeamMember(TeamMemberRequest teamMemberRequest, Long companyId, Long createdById);

    TeamMemberResponse updateTeamMember(Long id, TeamMemberRequest teamMemberRequest);
    List<TeamMemberResponse> getAllTeamMembers(Long companyId);
    TeamMemberResponse getTeamMemberById(Long id);
    void removeTeamMember(Long memberId);

    TeamMemberDetailsResponse getTeamMemberDetailsByMail(String memberMail);

    List<HashMap<String, Object>> getTeamMembersWithIdAndName(Long companyId);

}
