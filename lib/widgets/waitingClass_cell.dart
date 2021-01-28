import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:flutter/cupertino.dart';

class CloudClassCell extends StatefulWidget {
  @override
  CloudClassCellState createState() => CloudClassCellState();
  final String scheduleTime;
  final String classTitle;
  final String classIntro;
  final String currentCount;
  final String totalCount;
  final bool isLine;

  const CloudClassCell({
    Key key,
    this.scheduleTime,
    this.classTitle,
    this.classIntro,
    this.currentCount,
    this.totalCount,
    this.isLine,
  }) : super(key: key);
}

class CloudClassCellState extends State<CloudClassCell> {
  @override
  void initState() {
    super.initState();
  }

  @override
  void dispose() {
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    ScreenUtil.init(context,
        designSize: Size(1024, 768), allowFontScaling: false);

    return InkWell(
      onTap: () async {
        const platform = const MethodChannel("toJava");
        String returnValue =
            await platform.invokeMethod("37e315e338c1d032418ea0911838a916");
        print("从原生Android的java方法返回的值是：" + returnValue);
      },
      child: Container(
        width: 375.w,
        height: 202.w,
        color: Colors.white,
        child: Stack(
          children: <Widget>[
            Padding(
              padding: EdgeInsets.only(left: 60.w, top: 3.w),
              child: Image.asset(
                'assets/icons/pb_icon_a@3x.png',
                width: 9.w,
                height: 11.w,
              ),
            ),
            Padding(
              padding: EdgeInsets.only(left: 83.w, top: 0.w),
              child: Text(
                this.widget.scheduleTime,
                style: TextStyle(
                    color: Color.fromRGBO(0, 0, 0, 0.54),
                    fontSize: 10.sp,
                    fontFamily: 'PingFangSC-Regular'),
              ),
            ),
            Padding(
                padding: EdgeInsets.only(left: 83.w, top: 22.w),
                child: InkWell(
                  onTap: () {},
                  child: Container(
                    width: 450.w,
                    height: 140.w,
                    decoration: BoxDecoration(
                        color: Colors.white,
                        borderRadius: BorderRadius.circular(4.w),
                        boxShadow: [
                          BoxShadow(
                              color: Color.fromRGBO(236, 236, 236, 0.5),
                              offset: Offset(0, 2.w), //阴影xy轴偏移量
                              blurRadius: 9.w, //阴影模糊程度
                              spreadRadius: 0

                              ///阴影扩散程度
                              )
                        ]),
                    child: Stack(children: <Widget>[
                      Padding(
                        padding: EdgeInsets.only(left: 12.w, top: 8.w),
                        child: Text(
                          this.widget.classTitle,
                          style: TextStyle(
                              color: Color.fromRGBO(0, 0, 0, 0.85),
                              fontSize: 16.sp,
                              fontWeight: FontWeight.bold,
                              fontFamily: 'PingFangSC-Regular'),
                        ),
                      ),
                      Padding(
                        padding: EdgeInsets.only(left: 12.w, top: 38.w),
                        child: Container(
                          width: 120.w,
                          height: 20.w,
                          decoration: BoxDecoration(
                            color: Color.fromRGBO(153, 211, 255, 0.45),
                            borderRadius: BorderRadius.circular(4.w),
                          ),
                          child: Stack(
                            children: <Widget>[
                              Padding(
                                padding: EdgeInsets.only(left: 4.w, top: 4.w),
                                child: Image.asset(
                                  'assets/icons/icon_class_book@3x.png',
                                  width: 12.w,
                                  height: 12.w,
                                ),
                              ),
                              Padding(
                                padding: EdgeInsets.only(left: 20.w, top: 3.w),
                                child: Text(
                                  this.widget.classIntro,
                                  maxLines: 1,
                                  overflow: TextOverflow.ellipsis,
                                  style: TextStyle(
                                      color: Color.fromRGBO(0, 145, 255, 1),
                                      fontSize: 10.sp),
                                ),
                              )
                            ],
                          ),
                        ),
                      ),
                      Padding(
                        padding: EdgeInsets.only(left: 382.w, top: 38.w),
                        child: Container(
                          //color: Colors.red,
                          width: 44.w,
                          height: 20.w,
                          decoration: BoxDecoration(
                            color: Color.fromRGBO(0, 145, 255, 1),
                            borderRadius: BorderRadius.circular(10.w),
                          ),
                          child: Center(
                            child: Text(
                              '待上课',
                              style: TextStyle(
                                  color: Colors.white,
                                  fontSize: 10.sp,
                                  fontFamily: 'PingFangSC-Regular'),
                            ),
                          ),
                        ),
                      ),
                      // Padding(
                      //   padding: EdgeInsets.only(left: 7.w, top: 96.w),
                      //   child: Container(
                      //     height: 30.w,
                      //     child: Row(
                      //       children: <Widget>[
                      //         Container(
                      //           width: 30.w,
                      //           height: 30.w,
                      //           margin: EdgeInsets.only(left: 6.w),
                      //           decoration: BoxDecoration(
                      //             color: Colors.grey,
                      //             borderRadius: BorderRadius.circular(15.w),
                      //           ),
                      //           child: Image.asset(
                      //             'assets/avatars/my__avatar_f_default@3x.png',
                      //             width: 30.w,
                      //             height: 30.w,
                      //           ),
                      //         ),
                      //         Container(
                      //           width: 30.w,
                      //           height: 30.w,
                      //           margin: EdgeInsets.only(left: 6.w),
                      //           decoration: BoxDecoration(
                      //             color: Colors.grey,
                      //             borderRadius: BorderRadius.circular(15.w),
                      //           ),
                      //           child: Image.asset(
                      //             'assets/avatars/my__avatar_d_default@3x.png',
                      //             width: 30.w,
                      //             height: 30.w,
                      //           ),
                      //         ),
                      //         Container(
                      //           width: 30.w,
                      //           height: 30.w,
                      //           margin: EdgeInsets.only(left: 6.w),
                      //           decoration: BoxDecoration(
                      //             color: Colors.grey,
                      //             borderRadius: BorderRadius.circular(15.w),
                      //           ),
                      //           child: Image.asset(
                      //             'assets/avatars/my__avatar_g_default@3x.png',
                      //             width: 30.w,
                      //             height: 30.w,
                      //           ),
                      //         ),
                      //         Container(
                      //           width: 30.w,
                      //           height: 30.w,
                      //           margin: EdgeInsets.only(left: 6.w),
                      //           decoration: BoxDecoration(
                      //             color: Colors.grey,
                      //             borderRadius: BorderRadius.circular(15.w),
                      //           ),
                      //           child: Image.asset(
                      //             'assets/avatars/my__avatar_l_default@3x.png',
                      //             width: 30.w,
                      //             height: 30.w,
                      //           ),
                      //         )
                      //       ],
                      //     ),
                      //   ),
                      // ),
                      Padding(
                        padding: EdgeInsets.only(left: 381.w, top: 103.w),
                        child: Row(
                          crossAxisAlignment: CrossAxisAlignment.end,
                          children: <Widget>[
                            Text(
                              '人数:',
                              style: TextStyle(
                                  color: Color.fromRGBO(0, 0, 0, 0.5),
                                  fontSize: 8.sp,
                                  fontFamily: 'PingFangSC-Regular'),
                            ),
                            Text(
                              this.widget.currentCount,
                              style: TextStyle(
                                  color: Color.fromRGBO(0, 0, 0, 0.5),
                                  fontSize: 12.sp,
                                  fontFamily: 'PingFangSC-Regular'),
                            ),
                            Text(
                              '/',
                              style: TextStyle(
                                  color: Color.fromRGBO(0, 0, 0, 0.5),
                                  fontSize: 12.sp,
                                  fontFamily: 'PingFangSC-Regular'),
                            ),
                            Text(
                              this.widget.totalCount,
                              style: TextStyle(
                                  color: Color.fromRGBO(0, 145, 255, 1),
                                  fontSize: 12.sp,
                                  fontFamily: 'PingFangSC-Regular'),
                            )
                          ],
                        ),
                      )
                    ]),
                  ),
                )),
            Padding(
              padding: EdgeInsets.only(left: 63.w, top: 16.w),
              child: Image.asset(
                this.widget.isLine ? 'assets/icons/icon_class_line@3x.png' : '',
                width: 3.w,
                height: 160.w,
              ),
            ),
          ],
        ),
      ),
    );
  }
}
