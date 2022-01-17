import 'package:dio/dio.dart';
import 'package:json_annotation/json_annotation.dart';
import 'package:retrofit/retrofit.dart';

part 'authService.g.dart';

@RestApi(baseUrl: "https://vilagers.com/api")
abstract class RestClient {
  factory RestClient(Dio dio, {String baseUrl}) = _RestClient;

  @POST("/register")
  Future<UserModel> register(@Body() RegisterRequestModel body);
}


@JsonSerializable()
class RegisterRequestModel {
  String? user_name;
  String? email;
  String? password;

  RegisterRequestModel({this.user_name, this.email, this.password});

  factory RegisterRequestModel.fromJson(Map<String, dynamic> json) => _$RegisterRequestModelFromJson(json);
  Map<String, dynamic> toJson() => _$RegisterRequestModelToJson(this);
}

@JsonSerializable()
class UserModel {
  String? userId;
  String? userName;
  String? email;
  String? pass;

  UserModel({this.userId, this.userName, this.email, this.pass});

  factory UserModel.fromJson(Map<String, dynamic> json) => _$UserModelFromJson(json);
  Map<String, dynamic> toJson() => _$UserModelToJson(this);
}
