package com.example.throw_fornt.data.model.request

import com.example.throw_fornt.data.model.response.AuthCode
import com.example.throw_fornt.data.model.response.IdDup
import com.example.throw_fornt.data.model.response.Kakao
import com.example.throw_fornt.data.model.response.Login
import com.example.throw_fornt.data.model.response.Reissue
import com.example.throw_fornt.data.model.response.SendMail
import com.example.throw_fornt.data.model.response.UserSignUp
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface RetrofitService {

    @POST("/api/oauth/kakao")
    fun getKakao(@Body kakaoObj: KakaoObj) : Call<Kakao>


    @POST("/api/user/signup")
    fun getUser(@Body userSignUpObj: UserSignUpObj) : Call<String?>


    @POST("/api/user/iddup")
    fun getIdDup(@Body idDupObj: IdDupObj):Call<IdDup>

    @POST("/api/login")
    fun requestLogin(@Body loginObj: LoginObj):Call<Login>

    @POST("/api/login/reissue")
    fun reissueLogin(@Body reissueObj: ReissueObj):Call<Reissue>

    @POST("/api/mail/auth")
    fun sendMail(@Body sendMailObj:SendMailObj):Call<String?>

    @POST("/api/auth/code")
    fun checkCode(@Body authCodeObj: AuthCodeObj):Call<String?>
}