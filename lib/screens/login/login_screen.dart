import 'dart:async';

import 'package:Cloudgrain_teacher_teach/data/User.dart';
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
  final _userIDEditingController = TextEditingController();
  final _passwordEditingController = TextEditingController();

  String _userID;
  String _password;
  bool _isChecked = true;
  bool _isLoading;
  IconData _checkIcon = Icons.check_box;
  //验证码计时器
  Timer _countdownTimer;
  String _originalCountdownStr = '获取验证码';
  String _codeCountdownStr = '获取验证码';
  int _countdownNum = 59;

  @override
  void initState() {
    super.initState();
    _userIDEditingController.text = "16532701605";
    _passwordEditingController.text = "888888";
  }

  @override
  void dispose() {
    _countdownTimer?.cancel();
    _countdownTimer = null;
    _userIDEditingController.dispose();
    _passwordEditingController.dispose();
    super.dispose();
  }

  void _changeFormToLogin() {
    _formKey.currentState.reset();
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
        controller: this._userIDEditingController,
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
        controller: this._passwordEditingController,
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
                            "登录",
                            style: TextStyle(
                              color: Color.fromRGBO(255, 255, 255, 1),
                              fontFamily: 'PingFangSC-Semibold',
                              fontSize: 16.sp,
                            ),
                          ),
                          disabledColor: Color.fromRGBO(201, 204, 210, 1),
                          color: Color.fromRGBO(30, 94, 255, 1),
                          shape: RoundedRectangleBorder(
                              borderRadius: BorderRadius.circular(6.w)),
                          //borderSide: BorderSide(color: Colors.orange, width: 1),
                          onPressed: _userIDEditingController.text.isNotEmpty &&
                                  _passwordEditingController.text.isNotEmpty &&
                                  _isChecked
                              ? () {
                                  _onLogin();
                                }
                              : null,
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
                              onTap: () {
                                _codeCountdownStr == _originalCountdownStr
                                    ? SentVerify(context)
                                    : {};
                              },
                              child: Text(
                                _codeCountdownStr,
                                style: TextStyle(
                                    color: _codeCountdownStr ==
                                            _originalCountdownStr
                                        ? Color.fromRGBO(0, 81, 255, 1)
                                        : Color.fromRGBO(155, 157, 161, 1),
                                    fontSize: 12.sp,
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

  void SentVerify(BuildContext context) {
    FormData params = FormData.fromMap({
      'mobilePhone': _userID,
    });

    DioManager.getInstance().post("/user/sms/verify", params, (result) {
      print(result);
      showCupertinoDialog(
        context: context,
        builder: (context) {
          return CupertinoAlertDialog(
            title: Text('验证码已发送'),
            content: Text('\n验证码为888888'),
            actions: <Widget>[
              CupertinoDialogAction(
                child: Text('确认'),
                onPressed: () {
                  Navigator.of(context).pop();
                },
              ),
            ],
          );
        },
      );
      reGetCountdown();
      //验证通过提交数据
    }, (error) {
      print(error);
      showCupertinoDialog(
        context: context,
        builder: (context) {
          return CupertinoAlertDialog(
            title: Text('验证码发送失败'),
            content: Text('\n请重新检查手机号'),
            actions: <Widget>[
              CupertinoDialogAction(
                child: Text('确认'),
                onPressed: () {
                  Navigator.of(context).pop();
                },
              ),
            ],
          );
        },
      );
    });
  }

  void reGetCountdown() {
    setState(() {
      if (_countdownTimer != null) {
        return;
      }
      // Timer的第一秒倒计时是有一点延迟的，为了立刻显示效果可以添加下一行。
      _codeCountdownStr = '${_countdownNum--}秒后重新获取';
      _countdownTimer = new Timer.periodic(new Duration(seconds: 1), (timer) {
        setState(() {
          if (_countdownNum > 0) {
            _codeCountdownStr = '${_countdownNum--}秒后重新获取';
          } else {
            _codeCountdownStr = '获取验证码';
            _countdownNum = 59;
            _countdownTimer.cancel();
            _countdownTimer = null;
          }
        });
      });
    });
  }

  void _onLogin() {
    final form = _formKey.currentState;
    form.save();

    if (_userID.isEmpty) {
      showCupertinoDialog(
        context: context,
        builder: (context) {
          return CupertinoAlertDialog(
            title: Text('账号密码错误'),
            content: Text('\n用户账号不能为空'),
            actions: <Widget>[
              CupertinoDialogAction(
                child: Text('确认'),
                onPressed: () {
                  Navigator.of(context).pop();
                },
              ),
            ],
          );
        },
      );
      return;
    } else if (_password.isEmpty) {
      showCupertinoDialog(
        context: context,
        builder: (context) {
          return CupertinoAlertDialog(
            title: Text('账号密码错误'),
            content: Text('\n用户密码不能为空'),
            actions: <Widget>[
              CupertinoDialogAction(
                child: Text('确认'),
                onPressed: () {
                  Navigator.of(context).pop();
                },
              ),
            ],
          );
        },
      );
      return;
    }

    //网络请求
    FormData params = FormData.fromMap({
      'mobilePhone': _userID,
      'verifyCode': _password,
    });
    //自定义请求头
    Map<String, dynamic> httpHeaders = {
      'Accept': 'application/json,*/*',
      'Content-Type': 'application/json',
      'platformType': '5',
      'termType': 2,
      //'token': DioUtils.TOKEN
    };
    //置入自定义请求头
    DioManager.getInstance().setHeaders(httpHeaders);

    DioManager.getInstance().post("/user/phone/verifyCode/login", params,
        (result) {
      setUserLoginDataWithResult(result);
      setDataOfDictInfo();
      setDataOfNotificationCount();
      //验证通过提交数据
      GotoMainScreen(context, true);
    }, (error) {
      //失败后需要做的事
      showCupertinoDialog(
        context: context,
        builder: (context) {
          return CupertinoAlertDialog(
            title: Text('账号密码错误'),
            content: Text('\n请验证账号密码后再次输入'),
            actions: <Widget>[
              CupertinoDialogAction(
                child: Text('确认'),
                onPressed: () {
                  Navigator.of(context).pop();
                },
              ),
            ],
          );
        },
      );
    });
  }

  void setDataOfDictInfo() {
    //网络请求
    FormData params = FormData.fromMap({});
    DioManager.getInstance().setBaseUrl(1);
    //
    Map<String, dynamic> httpHeaders = {
      'Accept': 'application/json,*/*',
      'Content-Type': 'application/json',
      'accessToken': User.shared().accessToken,
    };

    DioManager.getInstance().setHeaders(httpHeaders);
    DioManager.getInstance().get("/sysDict/info", params, (result) {
      User.shared().sysDictionary = result['data'];
      User.shared().sysDictionaryCount = User.shared().sysDictionary.length;
      //验证通过提交数据
    }, (error) {});
  }

  void setDataOfNotificationCount() {
    //网络请求
    FormData params = FormData.fromMap({
      'pageNumber': 1,
      'pageSize': 8,
      "firstType": "class",
      "secondType": "studentJoin",
    });

    DioManager.getInstance().setBaseUrl(1);
    //
    Map<String, dynamic> httpHeaders = {
      'Accept': 'application/json,*/*',
      'Content-Type': 'application/json',
      'accessToken': User.shared().accessToken,

      //'token': DioUtils.TOKEN
    };
    DioManager.getInstance().setHeaders(httpHeaders);
    DioManager.getInstance().post("/pushMessage/list", params, (result) {
      setState(() {
        dynamic notificationList = result['data']['records'];
        int count = 0;
        for (int i = 0; i < notificationList.length; i++) {
          if (notificationList[i]['readStatus'].toString() == '1') {
            count++;
          }
        }
        User.shared().eventBus.fire(count.toString());
      });
      //验证通过提交数据
    }, (error) {});
    //
  }

  void setUserLoginDataWithResult(dynamic result) {
    User.shared().id = result['data']['id'];
    User.shared().userAccount = result['data']['userAccount'];
    User.shared().userType = result['data']['userType'];
    User.shared().userMail = result['data']['userMail'];
    User.shared().userImageId = result['data']['userImageId'];
    User.shared().imgUrl = result['data']['imgUrl'];
    User.shared().mobilePhone = result['data']['mobilePhone'];
    User.shared().actualName = result['data']['actualName'];
    User.shared().userSex = result['data']['userSex'];
    User.shared().loginLastAddr = result['data']['loginLastAddr'];
    User.shared().loginLastTime = result['data']['loginLastTime'];
    User.shared().nickName = result['data']['nickName'];
    User.shared().createBy = result['data']['createBy'];
    User.shared().createDate = result['data']['createDate'];
    User.shared().updateBy = result['data']['updateBy'];
    User.shared().updateDate = result['data']['updateDate'];
    User.shared().remarks = result['data']['remarks'];
    User.shared().delFlag = result['data']['delFlag'];
    User.shared().accessToken = result['data']['accessToken'];
    User.shared().meetingAccount = result['data']['meetingAccount'];
    User.shared().meetingPassword = result['data']['meetingPassword'];
    User.shared().registered = result['data']['registered'];
    User.shared().userFamily = result['data']['userFamily'];
    User.shared().publicTeacher = result['data']['publicTeacher'];
    User.shared().userTeacher = result['data']['userTeacher'];
    User.shared().userStudent = result['data']['userStudent'];
    User.shared().userTutorNight = result['data']['userTutorNight'];

    User.shared().price = result['data']['userTutorNight']['price'];

    User.shared().classTagDay = DateTime.now();
  }

  void GotoMainScreen(BuildContext context, bool isTeacher) {
    Navigator.of(context).pushAndRemoveUntil(
        CupertinoPageRoute(builder: (context) => RegisterChooseScreen()),
        (route) => route == null);
  }
}
