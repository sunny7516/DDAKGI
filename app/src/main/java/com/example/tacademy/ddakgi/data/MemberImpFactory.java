package com.example.tacademy.ddakgi.data;

import com.example.tacademy.ddakgi.data.HomeTimeline.ResHomePosting;
import com.example.tacademy.ddakgi.data.IntroTimeline.ResPosting;
import com.example.tacademy.ddakgi.data.Kakao.ReqKaKaoLogin;
import com.example.tacademy.ddakgi.data.Kakao.ResKaKaoLogin;
import com.example.tacademy.ddakgi.data.Member.ReqUpdateMemberInfo;
import com.example.tacademy.ddakgi.data.Member.ResMember;
import com.example.tacademy.ddakgi.data.RegisterRoom.ReqRegisterRoom;
import com.example.tacademy.ddakgi.data.RegisterRoom.ResStringString;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * 2. 카카오톡 로그인 및 최초사용자 등록
 * 3. 로그아웃
 * 4. 회원 등록
 * 5. 회원정보 조회
 * 6. 회원정보 수정
 * 7. 회원탈퇴하기
 * 8. 방있는 룸메이트 등록하기(room)
 * 9. 방 찾는 룸메이트 등록하기(mate)
 * 10. 룸메이트 글 조회하기
 * 11. 방있는 룸메이트 수정하기(room)
 * 12. 방 찾는 룸메이트 수정하기(mate)
 * 13. 룸메이트 글 삭제
 * 14. 로그인전 게시물 조회하기
 * 15. 게시글 목록보기 (all)
 * 16. 게시글 목록보기(room/mate)
 * 17. 게시글 상세페이지 조회하기
 * 18. 검색에서 주소 정보 가져오기(자동완성)
 * 19. 대화신청시 추가질문에 대한 답변 입력
 * 20. 대화신청 회원의 답변 보기
 * 22. 신고하기
 * 23. 게시물 찜하기
 * 24. 찜목록 조회하기
 * 25. 찜한 게시물 찜 해제하기
 */

public interface MemberImpFactory {
    // 임의 숫자는 로그인 후 me로 바뀌거나 생략할 예정

    // 2. 카카오톡 로그인 및 최초사용자 등록
    @POST("auth/kakao/token")
    Call<ResKaKaoLogin> resKaKaoLogin(@Body ReqKaKaoLogin reqKaKaoLogin);

    // 5. 회원 정보 조회
    @GET("members/3")
    Call<ResMember> resMember();

    // 6. 회원정보 수정
    @PUT("members/3")
    Call<ResStringString> resUpdateMember(@Body ReqUpdateMemberInfo reqUpdateMemberInfo);

    // 8. 방있는 룸메이트 등록하기(room)
    @POST("roommates/room/3")
    Call<ResStringString> registerRoom(@Body ReqRegisterRoom reqRegisterRoom);

    // 14. 로그인전 게시물 조회하기
    @GET("postings/beforelogin")
    Call<ResPosting> resPosting();

    // 15. 게시글 목록보기 (all)
    @GET("postings/list/3")
    Call<ResHomePosting> resAllPosting();
                                    /*@Query("location1") String location1,
                                      @Query("location2") String location2,
                                      @Query("location3") String location3,
                                      @Query("roomtype1") int roomtype1,
                                      @Query("roomtype2") int roomtype2,
                                      @Query("roomtype3") int roomtype3,
                                      @Query("roomtype4") int roomtype4,
                                      @Query("roomtype5") int roomtype5,
                                      @Query("deposit") int deposit,
                                      @Query("rent") int rent,
                                      @Query("available_date") String available_date);*/

    // 16. 게시글 목록보기(room/mate)
    @GET("postings/list/{roomming}/3")
    Call<ResHomePosting> resRoomMatePosting(@Path("roomming") int roomming);
                                    /*@Query("location1") String location1,
                                      @Query("location2") String location2,
                                      @Query("location3") String location3,
                                      @Query("roomtype1") int roomtype1,
                                      @Query("roomtype2") int roomtype2,
                                      @Query("roomtype3") int roomtype3,
                                      @Query("roomtype4") int roomtype4,
                                      @Query("roomtype5") int roomtype5,
                                      @Query("deposit") int deposit,
                                      @Query("rent") int rent,
                                      @Query("available_date") String available_date);*/
}
