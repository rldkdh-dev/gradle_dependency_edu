package kr.go.tech.protection.user.domain.signup.dao;

import kr.go.tech.protection.user.domain.signup.dto.SignUpPO;
import kr.go.tech.protection.user.domain.signup.dto.SignUpVO;
import kr.go.tech.protection.user.domain.signup.dto.SignUpVO.DefaultEntMemberVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SignUpDAO {
	int insertGenMember(SignUpVO.GenSignUpRequestVO requestVO);

	int selectGenIdDuplicate(String searchId);

	int selectGenMemberByPassAuth(SignUpPO.PassAuthRequestPO requestPO);

	DefaultEntMemberVO selectEntMemberByBusinessNumber(String businessNumber);

	int insertEntPrcpt(SignUpVO.InsertEntPrcptRequestVO insertEntPrcptRequestParam);

	int insertEntMember(SignUpVO.EntSignUpRequestVO requestVO);

	int selectEntMemberJointCertInfo(SignUpPO.JointCertInfoRequestPO requestPO);
}
