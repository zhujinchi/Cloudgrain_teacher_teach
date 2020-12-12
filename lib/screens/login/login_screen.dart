import 'package:Cloudgrain_teacher_teach/screens/login/register_choose_screen.dart';
import 'package:Cloudgrain_teacher_teach/widgets/network/dio_manager.dart';
import 'package:flutter/material.dart';
import 'package:Cloudgrain_teacher_teach/widgets/widgets.dart';
//import 'package:Cloudgrain_teacher_teach/screens/login/register_choose_screen.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:Cloudgrain_teacher_teach/widgets/network/net_api.dart';
import 'package:dio/dio.dart';
import 'dart:io';

class LoginScreen extends StatefulWidget {
  @override
  _LoginScreenState createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  final _formKey = new GlobalKey<FormState>();

  String _userID;
  String _password;
  bool _isChecked = true;
  bool _isLoading;
  IconData _checkIcon = Icons.check_box;

  void _changeFormToLogin() {
    _formKey.currentState.reset();
  }

  void _onLogin() {
    final form = _formKey.currentState;
    form.save();

    //网络请求
    FormData params = FormData.fromMap({
      'userName': 'XH6768768762',
      'password': 'ad123&368',
    });

    DioManager.getInstance().post("/user/userName/login", params, (result) {
      print(result);
      //请求成功需要做的事
    }, (error) {
      print(error + '123');
      //失败后需要做的事
    });

    // if (_userID == '') {
    //   _showMessageDialog('账号不可为空');
    //   return;
    // }
    // if (_password == '') {
    //   _showMessageDialog('密码不可为空');
    //   return;
    // }
    // if ((_formKey.currentState as FormState).validate()) {
    //   //验证通过提交数据
    GotoMainScreen(context, true);
    // }
  }

  void _showMessageDialog(String message) {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        // return object of type Dialog
        return AlertDialog(
          title: new Text('提示'),
          content: new Text(message),
          actions: <Widget>[
            new FlatButton(
              child: new Text("ok"),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
          ],
        );
      },
    );
  }

  Widget _showAccountInput() {
    return Container(
      height: 40.w,
      padding: EdgeInsets.only(top: 0.w),
      child: new TextFormField(
        maxLines: 1,
        maxLength: 16,
        keyboardType: TextInputType.phone,
        autofocus: false,
        style: TextStyle(fontSize: 14.sp),
        decoration: new InputDecoration(
          contentPadding: EdgeInsets.only(left: 0.w),
          border: InputBorder.none,
          hintText: '手机号',
          counterText: '',
          hintStyle: TextStyle(
              color: Color.fromRGBO(155, 157, 161, 1),
              fontFamily: 'PingFangSC-Medium',
              fontSize: 14.sp),
        ),
        validator: (v) {
          v.trim().length > 0 ? null : "用户名不能为空";
        },
        onSaved: (value) => _userID = value.trim(),
      ),
    );
  }

  Widget _showPasswordInput() {
    return Container(
      height: 40.w,
      padding: EdgeInsets.only(top: 0.w),
      child: new TextFormField(
        maxLines: 1,
        maxLength: 6,
        maxLengthEnforced: true,
        //obscureText: true,
        autofocus: false,

        style: TextStyle(fontSize: 14.sp),
        decoration: new InputDecoration(
          contentPadding: EdgeInsets.only(left: 0.w),
          border: InputBorder.none,
          hintText: '请输入验证码',
          hintStyle: TextStyle(
              color: Color.fromRGBO(155, 157, 161, 1),
              fontFamily: 'PingFangSC-Medium',
              fontSize: 14.sp),
          counterText: '',
        ),
        onSaved: (value) => _password = value.trim(),
      ),
    );
  }

  Widget _showUserTermAndPrivacy() {
    return Container(
      //color: Colors.red,
      padding: EdgeInsets.fromLTRB(0, 16.w, 0, 0),
      child: Row(
        children: <Widget>[
          InkWell(
            onTap: () {
              setState(() {
                _isChecked = !_isChecked;
              });
            },
            child: Container(
              width: 12.w,
              height: 12.w,
              // decoration: BoxDecoration(
              //     shape: BoxShape.circle,
              color: _isChecked
                  ? Color.fromRGBO(0, 81, 255, 1)
                  : Color.fromRGBO(238, 238, 238, 1),
              //),
              //child: Padding(
              padding: const EdgeInsets.all(0.0),
              child: _isChecked
                  ? Icon(
                      Icons.check,
                      size: 12.w,
                      color: Colors.white,
                    )
                  : Icon(
                      Icons.brightness_1,
                      size: 12.w,
                      color: Color.fromRGBO(238, 238, 238, 1),
                    ),
              //),
            ),
          ),
          Container(
            padding: EdgeInsets.only(left: 6.w),
            child: RichText(
                text: TextSpan(
                    text: '我已阅读并同意',
                    style: TextStyle(
                        color: Color.fromRGBO(155, 157, 161, 1),
                        fontSize: 12.w),
                    children: <TextSpan>[
                  TextSpan(
                      text: '“用户协议”',
                      style: TextStyle(
                        color: Color.fromRGBO(30, 94, 255, 1),
                        // decoration: TextDecoration.underline
                      )),
                  TextSpan(text: '和'),
                  TextSpan(
                      text: '“隐私政策”',
                      style: TextStyle(
                        color: Color.fromRGBO(30, 94, 255, 1),
                        // decoration: TextDecoration.underline
                      ))
                ])),
          )
        ],
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    ScreenUtil.init(context,
        designSize: Size(1120, 768), allowFontScaling: false);
    return Scaffold(
        backgroundColor: Color.fromRGBO(255, 255, 255, 1),
        resizeToAvoidBottomPadding: false,
        body: Center(
            child: GestureDetector(
          onTap: () {
            FocusScopeNode currentFocus = FocusScope.of(context);
            if (!currentFocus.hasPrimaryFocus &&
                currentFocus.focusedChild != null) {
              FocusManager.instance.primaryFocus.unfocus();
            }
          },
          child: Stack(
            children: <Widget>[
              Padding(
                padding: EdgeInsets.fromLTRB(0, 0, 0, 0),
                child: Container(
                  width: 1024.w,
                  height: 786.h,
                  color: Colors.white,
                  //color: Color.fromRGBO(255, 255, 255, 1),
                ),
              ),
              Padding(
                padding: EdgeInsets.fromLTRB(381.w, 131.w, 0, 0),
                child: Image.asset(
                  'assets/images/logo_wfd@3x.png',
                  width: 261.w,
                  height: 71.w,
                ),
              ),
              Padding(
                padding: EdgeInsets.fromLTRB(439.w, 225.w, 0, 0),
                child: Text(
                  '学贵得师，亦贵得友',
                  style: TextStyle(
                      color: Color.fromRGBO(59, 61, 79, 1),
                      fontSize: 16.sp,
                      fontWeight: FontWeight.bold,
                      fontFamily: 'PingFangSC-Medium'),
                ),
              ),
              Padding(
                padding: EdgeInsets.fromLTRB(257.w, 293.w, 0, 0),
                child: Container(
                  width: 542.w,
                  height: 240.w,
                  decoration:
                      BoxDecoration(borderRadius: BorderRadius.circular(6.w)),
                  padding: EdgeInsets.fromLTRB(16.w, 0.w, 16.w, 0),
                  child: Column(
                    children: <Widget>[
                      Form(
                        key: _formKey,
                        child: Container(
                          height: 108.h,
                          child: Column(
                            children: <Widget>[
                              _showAccountInput(),
                              Container(
                                height: 1.w,
                                color: Color.fromRGBO(228, 228, 228, 1),
                              ),
                              Container(
                                height: 10.w,
                              ),
                              _showPasswordInput(),
                              Container(
                                height: 1.w,
                                color: Color.fromRGBO(228, 228, 228, 1),
                              ),
                            ],
                          ),
                        ),
                      ),
                      Container(
                        //color: Colors.red,
                        width: 510.w,
                        height: 44.w,
                        margin: EdgeInsets.only(top: 18.w),
                        padding: EdgeInsets.fromLTRB(0, 0, 0, 0),
                        child: MaterialButton(
                          child: Text(
                            '登录',
                            style: TextStyle(
                              color: Color.fromRGBO(255, 255, 255, 1),
                              fontFamily: 'PingFangSC-Semibold',
                              fontSize: 16.sp,
                            ),
                          ),

                          color: Color.fromRGBO(30, 94, 255, 1),
                          shape: RoundedRectangleBorder(
                              borderRadius: BorderRadius.circular(6.w)),
                          //borderSide: BorderSide(color: Colors.orange, width: 1),
                          onPressed: () {
                            _onLogin();
                          },
                        ),
                      ),
                      Container(
                        alignment: Alignment.topLeft,
                        child: _showUserTermAndPrivacy(),
                      )
                    ],
                  ),
                ),
              ),
              Padding(
                  padding: EdgeInsets.only(left: 695.w, top: 358.w),
                  child: Container(
                      width: 71.w,
                      height: 20.w,
                      child: Center(
                          child: InkWell(
                              onTap: () {},
                              child: Text(
                                '获取验证码',
                                style: TextStyle(
                                    color: Color.fromRGBO(0, 81, 255, 1),
                                    fontSize: 14.sp,
                                    fontFamily: 'PingFangSC-Regular'),
                              ))))),
              Padding(
                  padding: EdgeInsets.only(left: 118.w, top: 602.w),
                  child: Container(
                    width: 330.w,
                    height: 1.w,
                    color: Color.fromRGBO(201, 204, 210, 0.3),
                  )),
              Padding(
                  padding: EdgeInsets.fromLTRB(476.w, 593.w, 0, 0),
                  child: Container(
                    width: 73.w,
                    height: 17.w,
                    child: Center(
                      child: Text(
                        '其他登陆方式',
                        style: TextStyle(
                            color: Color.fromRGBO(134, 136, 140, 1),
                            fontSize: 12.sp,
                            fontFamily: 'PingFangSC-Regular'),
                      ),
                    ),
                  )),
              Padding(
                  padding: EdgeInsets.only(left: 577.w, top: 602.w),
                  child: Container(
                    width: 330.w,
                    height: 1.w,
                    color: Color.fromRGBO(201, 204, 210, 0.3),
                  )),
              Padding(
                  padding: EdgeInsets.only(left: 414.w, top: 628.w),
                  child: InkWell(
                      onTap: () {},
                      child: Container(
                          width: 36.w,
                          height: 36.w,
                          child: Image.asset(
                            'assets/icons/registered_icon_c@3x.png',
                            fit: BoxFit.fill,
                          )))),
              Padding(
                  padding: EdgeInsets.only(left: 574.w, top: 628.w),
                  child: InkWell(
                      onTap: () {},
                      child: Container(
                          width: 36.w,
                          height: 36.w,
                          child: Image.asset(
                            'assets/icons/registered_icon_d@3x.png',
                            fit: BoxFit.fill,
                          )))),
            ],
          ),
        )));
  }

  void GotoMainScreen(BuildContext context, bool isTeacher) {
    Navigator.of(context)
        .push(CupertinoPageRoute(builder: (context) => RegisterChooseScreen()));
  }
}
