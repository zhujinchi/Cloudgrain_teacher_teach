import 'package:Cloudgrain_teacher_teach/data/User.dart';
import 'package:Cloudgrain_teacher_teach/screens/read/read_catalog_screen.dart';
import 'package:Cloudgrain_teacher_teach/widgets/network/dio_manager.dart';
import 'package:dio/dio.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';

class ListViewBookPage extends StatefulWidget {
  final String bookType;
  ListViewBookPage({Key key, this.bookType}) : super(key: key);

  @override
  _ListViewBookPageState createState() {
    return _ListViewBookPageState();
  }
}

class _ListViewBookPageState extends State<ListViewBookPage> {
  dynamic list = [];

  @override
  void initState() {
    _refresh();
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
    return new RefreshIndicator(
      onRefresh: _refresh,
      child: ListView.builder(
          itemCount: list.length ~/ 2,
          itemBuilder: (BuildContext context, int a) {
            return getRow(2 * a);
          }),
    );
  }

  Widget getRow(int a) {
    return GestureDetector(
      child: Row(
        children: <Widget>[
          Padding(
              padding:
                  EdgeInsets.fromLTRB(142.w, a == 0 ? 38.w : 9.w, 0.w, 9.w),
              child: Container(
                  width: 325.w,
                  height: 140.w,
                  decoration: BoxDecoration(
                      color: Color.fromRGBO(255, 255, 255, 1),
                      borderRadius: BorderRadius.circular(6.w),
                      boxShadow: [
                        BoxShadow(
                            color: Color.fromRGBO(236, 236, 236, 1),
                            offset: Offset(0, 2.w), //阴影xy轴偏移量
                            blurRadius: 7.w, //阴影模糊程度
                            spreadRadius: 0

                            ///阴影扩散程度
                            )
                      ]),
                  child: Stack(
                    children: <Widget>[
                      Padding(
                        padding: EdgeInsets.only(left: 292.w, top: 0.w),
                        child: Image.asset(
                          'assets/icons/yiqixue_icon_read@3x.png',
                          width: 34.w,
                          height: 17.w,
                          fit: BoxFit.fill,
                        ),
                      ),
                      Padding(
                        padding: EdgeInsets.only(left: 4.w, top: 4.w),
                        child: Container(
                            width: 114.w,
                            height: 132.w,
                            decoration: BoxDecoration(
                                image: DecorationImage(
                                    image: NetworkImage(
                                      //this.memberImgUrl,
                                      list[a]['imageUrl'],
                                    ),
                                    fit: BoxFit.fitHeight),
                                color: Color.fromRGBO(247, 247, 247, 1),
                                borderRadius: BorderRadius.circular(6.w))),
                      ),
                      Padding(
                        padding: EdgeInsets.only(left: 128.w, top: 19.w),
                        child: Text(
                          list[a]['bookName'],
                          style: TextStyle(
                              color: Color.fromRGBO(0, 0, 0, 0.85),
                              fontWeight: FontWeight.bold,
                              fontSize: 14.sp,
                              fontFamily: 'PingFangSC-Regular'),
                        ),
                      ),
                      Padding(
                        padding: EdgeInsets.only(left: 129.w, top: 47.w),
                        child: Text(
                          list[a]['author'],
                          style: TextStyle(
                              color: Color.fromRGBO(92, 92, 92, 1),
                              fontSize: 12.sp,
                              fontFamily: 'PingFangSC-Regular'),
                        ),
                      ),
                      Padding(
                        padding: EdgeInsets.only(left: 129.w, top: 72.w),
                        child: Text(
                          '876人参加与打卡',
                          style: TextStyle(
                              color: Color.fromRGBO(92, 92, 92, 1),
                              fontSize: 10.sp,
                              fontFamily: 'PingFangSC-Regular'),
                        ),
                      ),
                      Padding(
                          padding: EdgeInsets.fromLTRB(129.w, 93.w, 0, 0),
                          child: Container(
                              width: 27.w,
                              height: 27.w,
                              decoration: BoxDecoration(
                                  color: Color.fromRGBO(216, 216, 216, 1),
                                  borderRadius:
                                      BorderRadius.circular(13.5.w)))),
                      Padding(
                          padding: EdgeInsets.fromLTRB(149.w, 93.w, 0, 0),
                          child: Container(
                              width: 27.w,
                              height: 27.w,
                              decoration: BoxDecoration(
                                  color: Color.fromRGBO(167, 167, 167, 1),
                                  borderRadius:
                                      BorderRadius.circular(13.5.w)))),
                      Padding(
                          padding: EdgeInsets.fromLTRB(167.w, 93.w, 0, 0),
                          child: Container(
                              width: 27.w,
                              height: 27.w,
                              decoration: BoxDecoration(
                                  color: Color.fromRGBO(120, 120, 120, 1),
                                  borderRadius:
                                      BorderRadius.circular(13.5.w)))),
                      Padding(
                          padding: EdgeInsets.only(left: 259.w, top: 96.w),
                          child: Container(
                            width: 60.w,
                            height: 27.w,
                            child: MaterialButton(
                              elevation: 0,
                              child: Text(
                                '去测评',
                                style: TextStyle(
                                  color: Color.fromRGBO(255, 255, 255, 1),
                                  fontFamily: 'PingFangSC-Medium',
                                  fontSize: 12.sp,
                                ),
                              ),
                              padding: EdgeInsets.fromLTRB(0, 0, 0, 0),
                              color: Color.fromRGBO(0, 145, 255, 1),
                              shape: RoundedRectangleBorder(
                                  borderRadius: BorderRadius.circular(7.w)),
                              //borderSide: BorderSide(color: Colors.orange, width: 1),
                              onPressed: () {
                                Navigator.of(context).push(CupertinoPageRoute(
                                    builder: (context) => ReadCatalogScreen(
                                          bookTitle: list[a]['bookName'],
                                          bookId: list[a]['id'],
                                        )));
                              },
                            ),
                          ))
                    ],
                  ))),
          Padding(
              padding:
                  EdgeInsets.fromLTRB(18.w, a == 0 ? 38.w : 9.w, 25.w, 9.w),
              child: Container(
                  width: 325.w,
                  height: 140.w,
                  decoration: BoxDecoration(
                      color: Color.fromRGBO(255, 255, 255, 1),
                      borderRadius: BorderRadius.circular(6.w),
                      boxShadow: [
                        BoxShadow(
                            color: Color.fromRGBO(236, 236, 236, 1),
                            offset: Offset(0, 2.w), //阴影xy轴偏移量
                            blurRadius: 7.w, //阴影模糊程度
                            spreadRadius: 0

                            ///阴影扩散程度
                            )
                      ]),
                  child: Stack(
                    children: <Widget>[
                      Padding(
                        padding: EdgeInsets.only(left: 4.w, top: 4.w),
                        child: null,
                      ),
                      Padding(
                        padding: EdgeInsets.only(left: 292.w, top: 0.w),
                        child: Image.asset(
                          'assets/icons/yiqixue_icon_read@3x.png',
                          width: 34.w,
                          height: 17.w,
                          fit: BoxFit.fill,
                        ),
                      ),
                      Padding(
                        padding: EdgeInsets.only(left: 4.w, top: 4.w),
                        child: Container(
                            width: 114.w,
                            height: 132.w,
                            decoration: BoxDecoration(
                                image: DecorationImage(
                                    image: NetworkImage(
                                      //this.memberImgUrl,
                                      list[a + 1]['imageUrl'],
                                    ),
                                    fit: BoxFit.fitHeight),
                                color: Color.fromRGBO(247, 247, 247, 1),
                                borderRadius: BorderRadius.circular(6.w))),
                      ),
                      Padding(
                        padding: EdgeInsets.only(left: 128.w, top: 19.w),
                        child: Text(
                          list[a + 1]['bookName'],
                          style: TextStyle(
                              color: Color.fromRGBO(0, 0, 0, 0.85),
                              fontWeight: FontWeight.bold,
                              fontSize: 14.sp,
                              fontFamily: 'PingFangSC-Regular'),
                        ),
                      ),
                      Padding(
                        padding: EdgeInsets.only(left: 129.w, top: 47.w),
                        child: Text(
                          list[a + 1]['author'],
                          style: TextStyle(
                              color: Color.fromRGBO(92, 92, 92, 1),
                              fontSize: 12.sp,
                              fontFamily: 'PingFangSC-Regular'),
                        ),
                      ),
                      Padding(
                        padding: EdgeInsets.only(left: 129.w, top: 72.w),
                        child: Text(
                          '876人参加与打卡',
                          style: TextStyle(
                              color: Color.fromRGBO(92, 92, 92, 1),
                              fontSize: 10.sp,
                              fontFamily: 'PingFangSC-Regular'),
                        ),
                      ),
                      Padding(
                          padding: EdgeInsets.fromLTRB(129.w, 93.w, 0, 0),
                          child: Container(
                              width: 27.w,
                              height: 27.w,
                              decoration: BoxDecoration(
                                  color: Color.fromRGBO(216, 216, 216, 1),
                                  borderRadius:
                                      BorderRadius.circular(13.5.w)))),
                      Padding(
                          padding: EdgeInsets.fromLTRB(149.w, 93.w, 0, 0),
                          child: Container(
                              width: 27.w,
                              height: 27.w,
                              decoration: BoxDecoration(
                                  color: Color.fromRGBO(167, 167, 167, 1),
                                  borderRadius:
                                      BorderRadius.circular(13.5.w)))),
                      Padding(
                          padding: EdgeInsets.fromLTRB(167.w, 93.w, 0, 0),
                          child: Container(
                              width: 27.w,
                              height: 27.w,
                              decoration: BoxDecoration(
                                  color: Color.fromRGBO(120, 120, 120, 1),
                                  borderRadius:
                                      BorderRadius.circular(13.5.w)))),
                      Padding(
                          padding: EdgeInsets.only(left: 259.w, top: 96.w),
                          child: Container(
                            width: 60.w,
                            height: 27.w,
                            child: MaterialButton(
                              elevation: 0,
                              child: Text(
                                '去测评',
                                style: TextStyle(
                                  color: Color.fromRGBO(255, 255, 255, 1),
                                  fontFamily: 'PingFangSC-Medium',
                                  fontSize: 12.sp,
                                ),
                              ),
                              padding: EdgeInsets.fromLTRB(0, 0, 0, 0),
                              color: Color.fromRGBO(0, 145, 255, 1),
                              shape: RoundedRectangleBorder(
                                  borderRadius: BorderRadius.circular(7.w)),
                              //borderSide: BorderSide(color: Colors.orange, width: 1),
                              onPressed: () {
                                Navigator.of(context).push(CupertinoPageRoute(
                                    builder: (context) => ReadCatalogScreen(
                                          bookTitle: list[a]['bookName'],
                                          bookId: list[a]['id'],
                                        )));
                              },
                            ),
                          ))
                    ],
                  ))),
        ],
      ), //tap事件触发
      onTap: _addList(a),
    );
  }

  _addList(int a) {
    //list.add(Text("我是第${a + 1}个列表"));
    //print('我是第${a}个列表');
  }

  Future<void> _refresh() async {
    await Future<Null>.delayed(Duration(seconds: 0), () {
      setDataOfBook();
      return null;
    });
  }

  void setDataOfBook() {
    //网络请求
    FormData params = FormData.fromMap({
      'pageNum': 1,
      'pageSize': 1000,
      'courseType': this.widget.bookType,
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
    DioManager.getInstance().post("/reading/acton/course/page", params,
        (result) {
      setState(() {
        this.list = result['data']['records'];
      });
      //验证通过提交数据
    }, (error) {});
    //
  }
}
