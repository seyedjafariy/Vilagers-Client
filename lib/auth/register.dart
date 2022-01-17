import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'dart:ui';
import 'package:flutter/gestures.dart';
import 'package:flutter/services.dart';
import 'package:fluttertoast/fluttertoast.dart';
import '../main.dart';
import 'authService.dart';

class RegisterPage extends StatefulWidget {
  const RegisterPage({Key? key}) : super(key: key);

  @override
  _RegisterPageState createState() => _RegisterPageState();
}

class _RegisterPageState extends State<RegisterPage> {
  var username = "";
  var email = "";
  var password = "";

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return Scaffold(
      body: ScrollConfiguration(
        behavior: MyBehavior(),
        child: SingleChildScrollView(
          child: SizedBox(
            height: size.height,
            child: Stack(
              children: [
                SizedBox(
                  height: size.height,
                  child: Image.asset(
                    'assets/images/background_2.jpg',
                    // #Image Url: https://unsplash.com/photos/bOBM8CB4ZC4
                    fit: BoxFit.fitHeight,
                  ),
                ),
                Center(
                  child: Column(
                    children: [
                      const Expanded(
                        child: SizedBox(),
                      ),
                      Expanded(
                        flex: 7,
                        child: ClipRRect(
                          borderRadius: BorderRadius.circular(30),
                          child: BackdropFilter(
                            filter: ImageFilter.blur(sigmaY: 8, sigmaX: 8),
                            child: SizedBox(
                              width: size.width * .9,
                              child: Column(
                                mainAxisAlignment:
                                    MainAxisAlignment.spaceBetween,
                                children: [
                                  Padding(
                                    padding: EdgeInsets.only(
                                      top: size.width * .15,
                                      bottom: size.width * .1,
                                    ),
                                    child: Text(
                                      'لوگو',
                                      style: TextStyle(
                                        fontSize: 32,
                                        fontWeight: FontWeight.w600,
                                        color: Colors.white.withOpacity(.8),
                                      ),
                                    ),
                                  ),
                                  component(
                                      Icons.account_circle_outlined,
                                      'نام کاربری',
                                      false,
                                      false,
                                      true,
                                      (text) => username = text),
                                  component(
                                      Icons.email_outlined,
                                      'ایمیل',
                                      false,
                                      true,
                                      true,
                                      (text) => email = text),
                                  component(
                                      Icons.lock_outline,
                                      'رمز عبور',
                                      true,
                                      false,
                                      false,
                                      (text) => password = text),
                                  Row(
                                    mainAxisAlignment:
                                        MainAxisAlignment.spaceAround,
                                    children: [
                                      TextButton(
                                          onPressed: () =>
                                              _showToast(context, 'فراموش'),
                                          child: const Text(
                                            'فراموشی رمز عبور',
                                            style: TextStyle(
                                                color: Colors.white,
                                                fontFamily: 'Dana',
                                                fontSize: 12),
                                          )),
                                      TextButton(
                                          onPressed: () =>
                                              Fluttertoast.showToast(
                                                msg: 'Sign-In button pressed',
                                              ),
                                          child: const Text(
                                            'ورود',
                                            style: TextStyle(
                                                color: Colors.white,
                                                fontFamily: 'Dana',
                                                fontSize: 12),
                                          )),
                                    ],
                                  ),
                                  SizedBox(height: size.width * .3),
                                  InkWell(
                                    splashColor: Colors.blue,
                                    highlightColor: Colors.red,
                                    onTap: () {
                                      HapticFeedback.lightImpact();
                                      _callRegister();
                                    },
                                    child: Container(
                                      margin: EdgeInsets.only(
                                        bottom: size.width * .05,
                                      ),
                                      height: size.width / 8,
                                      width: size.width / 1.25,
                                      alignment: Alignment.center,
                                      decoration: BoxDecoration(
                                        color: Colors.black.withOpacity(.3),
                                        borderRadius: BorderRadius.circular(20),
                                      ),
                                      child: const Text(
                                        'ثبت نام',
                                        style: TextStyle(
                                          color: Colors.white,
                                          fontSize: 20,
                                          fontWeight: FontWeight.w600,
                                        ),
                                      ),
                                    ),
                                  ),
                                ],
                              ),
                            ),
                          ),
                        ),
                      ),
                      const Expanded(
                        child: SizedBox(),
                      ),
                    ],
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }

  void _showToast(BuildContext context, String content) {
    final scaffold = ScaffoldMessenger.of(context);
    scaffold.showSnackBar(
      SnackBar(
        content: Text(content),
      ),
    );
  }

  void _callRegister() {
    globalLogger.i("password:" + password);
    globalLogger.i("email:" + email);
    globalLogger.i("username:" + username);

    dio.options.headers["Content-Type"] =
        "application/x-www-form-urlencoded"; // config your dio headers globally
    retrofitClient
        .register(RegisterRequestModel(
          user_name: username,
          email: email,
          password: password,
        ))
        .then((it) => _showToast(context, "موفق شدی"))
        .catchError((it) {
      switch (it.runtimeType) {
        case DioError:
          final res = (it as DioError).response;
          globalLogger
              .e("Got error : ${res?.statusCode} -> ${res?.statusMessage}");
          _showToast(
              context, "error: ${res?.statusCode} -> ${res?.statusMessage}");
          break;
        default:
          break;
      }
    });
  }

  Widget component(IconData icon, String hintText, bool isPassword,
      bool isEmail, bool hasNext, Function onChange) {
    Size size = MediaQuery.of(context).size;
    return Container(
      height: size.width / 8,
      width: size.width / 1.25,
      alignment: Alignment.center,
      padding: EdgeInsets.only(right: size.width / 30),
      decoration: BoxDecoration(
        color: Colors.black.withOpacity(.1),
        borderRadius: BorderRadius.circular(20),
      ),
      child: TextField(
        textAlign: TextAlign.center,
        style: TextStyle(
          color: Colors.white.withOpacity(.9),
        ),
        onChanged: (text) => {onChange(text)},
        obscureText: isPassword,
        keyboardType: isEmail ? TextInputType.emailAddress : TextInputType.text,
        textInputAction: hasNext ? TextInputAction.next : TextInputAction.done,
        decoration: InputDecoration(
          prefixIcon: Icon(
            icon,
            color: Colors.white.withOpacity(.8),
          ),
          border: InputBorder.none,
          hintMaxLines: 1,
          hintText: hintText,
          hintStyle: TextStyle(
            fontSize: 14,
            color: Colors.white.withOpacity(.5),
          ),
        ),
      ),
    );
  }
}

class MyBehavior extends ScrollBehavior {
  @override
  Widget buildViewportChrome(
    BuildContext context,
    Widget child,
    AxisDirection axisDirection,
  ) {
    return child;
  }
}
