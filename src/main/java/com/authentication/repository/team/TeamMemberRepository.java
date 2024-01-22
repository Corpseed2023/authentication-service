package com.authentication.repository.team;


import com.authentication.model.teamMemberModel.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember,Long> {
//    List<TeamMember> findAllByTeamId(Long teamId);

//    TeamMember findByMemberMail(String memberMail);

    List<TeamMember> findAllByCompanyId(Long companyId);

    List<TeamMember> findByMemberMail(String memberMail);
}
