package com.example.tacademy.ddakgi.data;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 1. 페북 로그인 및 최초사용자 등록
 2. 카카오톡 로그인 및 최초사용자 등록
 3. 로그아웃
 4. 회원 등록
 5. 회원정보 조회
 6. 회원정보 수정
 7. 회원탈퇴하기
 8. 방있는 룸메이트 등록하기(room)
 9. 방 찾는 룸메이트 등록하기(mate)
 10. 방있는 룸메이트 수정하기(room)
 11. 방 찾는 룸메이트 수정하기(mate)
 12. 룸메이트 글 삭제
 13. 게시글 목록보기 (all)
 14. 게시글 목록보기(room)
 15. 게시글 목록보기(mate)
 16. 검색에서 주소 정보 가져오기(자동완성)
 17. 대화신청시 추가질문에 대한 답변 입력
 18. 대화신청 회원의 답변 보기
 19. 채팅방 목록 보기
 20. 채팅방 내용보기
 21. 신고하기
 22. 게시물 찜하기
 23. 찜목록 조회하기
 24. 찜한 게시물 찜 해제하기
 25. 푸쉬알림전송하기
 */

public interface MemberImpFactory {
    // 로그인 후 me로 변경될 예정
    @POST("roommates/room/3")
    Call<ResRegisterRoom> registerRoom(@Body ReqRegisterRoom reqRegisterRoom);
}
