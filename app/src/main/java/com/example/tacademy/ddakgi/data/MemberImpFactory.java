package com.example.tacademy.ddakgi.data;

import com.example.tacademy.ddakgi.data.DeleteMe.ReqDeleteMe;
import com.example.tacademy.ddakgi.data.DetailPost.ResDetailPosting;
import com.example.tacademy.ddakgi.data.Heart.ReqSetHeart;
import com.example.tacademy.ddakgi.data.Heart.ResHeartPosting;
import com.example.tacademy.ddakgi.data.HomeTimeline.ResHomePosting;
import com.example.tacademy.ddakgi.data.IntroTimeline.ResPosting;
import com.example.tacademy.ddakgi.data.Kakao.ResKaKaoLogin;
import com.example.tacademy.ddakgi.data.Kakao.ResKaKaoLogout;
import com.example.tacademy.ddakgi.data.LifeStyleLogin.ReqLifeStyleLogin;
import com.example.tacademy.ddakgi.data.Member.ReqUpdateMemberInfo;
import com.example.tacademy.ddakgi.data.Member.ResMember;
import com.example.tacademy.ddakgi.data.Mypage.ResMypage;
import com.example.tacademy.ddakgi.data.RegisterMate.ReqRegisterMate;
import com.example.tacademy.ddakgi.data.RegisterRoom.ReqRegisterRoom;
import com.example.tacademy.ddakgi.data.RegisterRoom.ResStringString;
import com.example.tacademy.ddakgi.data.Report.ReqReport;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

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
    @GET("auth/kakao/token")
    Call<ResKaKaoLogin> resKaKaoLogin(@Query("access_token") String access_token);

    // 3. 로그아웃
    @GET("auth/logout")
    Call<ResKaKaoLogout> resKaKaoLogout();

    // 4. 회원 등록
    @PUT("members/login")
    Call<ResStringString> resLifeStyleLogin(@Body ReqLifeStyleLogin reqLifeStyleLogin);

    // 5. 회원 정보 조회
    @GET("members")
    Call<ResMember> resMember();

    // 6. 회원정보 수정
    @PUT("members")
    Call<ResStringString> resUpdateMember(@Body ReqUpdateMemberInfo reqUpdateMemberInfo);

    // 7. 회원 탈퇴하기
    @DELETE("members/me")
    Call<ResStringString> resDeleteMe(@Body ReqDeleteMe deleteMe);

    // 8. 방있는 룸메이트 등록하기(room)
    @POST("roommates/room")
    Call<ResStringString> registerRoom(@Body ReqRegisterRoom reqRegisterRoom);

    // 테스트 필요
    // 9. 방 찾는 룸메이트 등록하기(mate)
    @POST("roommates/mate")
    Call<ResStringString> registerMate(@Body ReqRegisterMate registerMate);

    // 13. 게시글 삭제하기
    @DELETE("roommates/{roommate_id}")
    Call<ResStringString> resDeletePosting(@Path("roommate_id") int roommate_id);

    // 14. 로그인전 게시물 조회하기
    @GET("postings/beforelogin")
    Call<ResPosting> resPosting();

    // 15. 게시글 목록보기 (all)
    @GET("postings/list")
    Call<ResHomePosting> resAllPosting(
            @Query("location1") String location1,
            @Query("location2") String location2,
            @Query("location3") String location3,
            @Query("roomtype1") int roomtype1,
            @Query("roomtype2") int roomtype2,
            @Query("roomtype3") int roomtype3,
            @Query("roomtype4") int roomtype4,
            @Query("roomtype5") int roomtype5,
            @Query("deposit") int deposit,
            @Query("rent") int rent,
            @Query("available_date") String available_date);

    // 16. 게시글 목록보기(room/mate)
    @GET("postings/list/{roomming}")
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

    // 17. 게시글 상세페이지 조회하기
    @GET("postings/detail/{roommate_id}")
    Call<ResDetailPosting> resDetailPosting(@Path("roommate_id") int roommate_id);

    // 22. 신고하기
    @POST("report")
    Call<ResStringString> resReport(@Body ReqReport reqReport);

    // 23. 게시물 찜하기
    @POST("heart")
    Call<ResStringString> resSetHeart(@Body ReqSetHeart reqSetHeart);

    // 24. 찜목록 조회하기
    @GET("heart")
    Call<ResHeartPosting> resHeartPosting();

    // 25. 찜한 게시물 찜 해제하기
    @DELETE("heart/{roommate_id}")
    Call<ResStringString> resDeleteHeart(@Path("roommate_id") int roommate_id);

    // 27. 마이페이지 조회하기
    @GET("postings/mypage")
    Call<ResMypage> resMypage();
}
