import 'package:Cloudgrain_teacher_teach/data/User.dart';
import 'package:Cloudgrain_teacher_teach/widgets/class_comment_cell.dart';
import 'package:Cloudgrain_teacher_teach/widgets/network/dio_manager.dart';
import 'package:Cloudgrain_teacher_teach/widgets/star_score.dart';
import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:flutter/cupertino.dart';

import 'package:flutter_screenutil/flutter_screenutil.dart';

class ClassCommentScreen extends StatefulWidget {
  String classId;
  int score;
  int scoreA;
  int scoreB;
  int scoreC;
  int scoreD;
  int scoreE;
  ClassCommentScreen(
      {Key key,
      this.classId,
      this.score,
      this.scoreA,
      this.scoreB,
      this.scoreC,
      this.scoreD,
      this.scoreE})
      : super(key: key);

  @override
  _ClassCommentScreenState createState() => _ClassCommentScreenState();
}

class _ClassCommentScreenState extends State<ClassCommentScreen>
    with SingleTickerProviderStateMixin {
  dynamic studentCommentList = '';
  @override
  void initState() {
    _refresh();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    ScreenUtil.init(context,
        designSize: Size(1024, 768), allowFontScaling: false);
    return Scaffold(
        backgroundColor: Colors.white,
        appBar: AppBar(
          centerTitle: true,
          backgroundColor: Colors.white,
          brightness: Brightness.light,
          elevation: 0.8,
          leading: IconButton(
            icon: const Icon(Icons.arrow_back_ios),
            color: Colors.grey,
            iconSize: 28.0,
            onPressed: () {
              Navigator.pop(context);
            },
          ),
          title: Text(
            '评价',
            style: TextStyle(
                color: Color.fromRGBO(15, 32, 67, 1),
                fontSize: 20.w,
                fontFamily: 'PingFangSC-Medium'),
          ),
        ),
        body: RefreshIndicator(
          onRefresh: _refresh,
          child: CustomScrollView(
            slivers: <Widget>[
              _buildScoreView(),
              studentCommentList.length == 0
                  ? _buildEmptyView()
                  : _buildCommentView(),
            ],
          ),
        ));
  }

  SliverToBoxAdapter _buildScoreView() {
    return SliverToBoxAdapter(
      child: Container(
        width: 1024.w,
        height: 271.w,
        color: Colors.white,
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          children: <Widget>[
            Container(
              height: 59.w,
              padding: EdgeInsets.only(top: 32.w),
              child: StarScore(
                score: this.widget.score == null ? 0 : this.widget.score,
                setHeight: 27.w,
              ),
            ),
            Container(
                padding: EdgeInsets.only(top: 19.w),
                child: Center(
                  child: Text(
                    '五星好评',
                    style: TextStyle(
                        color: Color.fromRGBO(155, 157, 161, 1),
                        fontSize: 14.sp,
                        fontFamily: 'PingFangSC-Regular'),
                  ),
                )),
            Container(
                padding: EdgeInsets.only(left: 438.w, top: 20.w),
                child: Row(
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: <Widget>[
                    Text(
                      '专业性',
                      style: TextStyle(
                          color: Color.fromRGBO(15, 32, 67, 1),
                          fontSize: 14.sp,
                          fontFamily: 'PingFangSC-Regular'),
                    ),
                    Container(
                      width: 37.w,
                    ),
                    StarScore(
                      score:
                          this.widget.scoreA == null ? 0 : this.widget.scoreA,
                      setHeight: 14.w,
                    ),
                  ],
                )),
            Container(
                padding: EdgeInsets.only(left: 438.w, top: 6.w),
                child: Row(
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: <Widget>[
                    Text(
                      '趣味性',
                      style: TextStyle(
                          color: Color.fromRGBO(15, 32, 67, 1),
                          fontSize: 14.sp,
                          fontFamily: 'PingFangSC-Regular'),
                    ),
                    Container(
                      width: 37.w,
                    ),
                    StarScore(
                      score:
                          this.widget.scoreB == null ? 0 : this.widget.scoreB,
                      setHeight: 14.w,
                    ),
                  ],
                )),
            Container(
                padding: EdgeInsets.only(left: 438.w, top: 6.w),
                child: Row(
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: <Widget>[
                    Text(
                      '专业性',
                      style: TextStyle(
                          color: Color.fromRGBO(15, 32, 67, 1),
                          fontSize: 14.sp,
                          fontFamily: 'PingFangSC-Regular'),
                    ),
                    Container(
                      width: 37.w,
                    ),
                    StarScore(
                      score:
                          this.widget.scoreC == null ? 0 : this.widget.scoreC,
                      setHeight: 14.w,
                    ),
                  ],
                )),
            Container(
                padding: EdgeInsets.only(left: 438.w, top: 6.w),
                child: Row(
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: <Widget>[
                    Text(
                      '专业性',
                      style: TextStyle(
                          color: Color.fromRGBO(15, 32, 67, 1),
                          fontSize: 14.sp,
                          fontFamily: 'PingFangSC-Regular'),
                    ),
                    Container(
                      width: 37.w,
                    ),
                    StarScore(
                      score:
                          this.widget.scoreD == null ? 0 : this.widget.scoreD,
                      setHeight: 14.w,
                    ),
                  ],
                )),
            Container(
                padding: EdgeInsets.only(left: 438.w, top: 6.w),
                child: Row(
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: <Widget>[
                    Text(
                      '专业性',
                      style: TextStyle(
                          color: Color.fromRGBO(15, 32, 67, 1),
                          fontSize: 14.sp,
                          fontFamily: 'PingFangSC-Regular'),
                    ),
                    Container(
                      width: 37.w,
                    ),
                    StarScore(
                      score: this.widget.scoreE == 0 ? 0 : this.widget.scoreE,
                      setHeight: 14.w,
                    ),
                  ],
                )),
            Container(
              padding: EdgeInsets.only(top: 29.w),
              child: Container(
                width: 1024.w,
                height: 1.w,
                color: Color.fromRGBO(201, 204, 210, 1),
              ),
            )
          ],
        ),
      ),
    );
  }

  Widget _buildCommentView() {
    return SliverFixedExtentList(
      itemExtent: 135.w,
      delegate:
          new SliverChildBuilderDelegate((BuildContext context, int index) {
        //创建列表项
        return new Container(
          alignment: Alignment.center,
          child: new Container(
            color: Color.fromRGBO(255, 255, 255, 0),
            child: Container(
              width: 1024.w,
              height: 135.w,
              padding: EdgeInsets.fromLTRB(0.w, 0.w, 0.w, 0.w),
              child: ClassCommentCell(
                setHeight: 14.w,
                score: int.parse(
                    this.studentCommentList[index]['score'].toString()),
                studentName:
                    this.studentCommentList[index]['nickName'].toString(),
                studentImagUrl:
                    this.studentCommentList[index]['imgUrl'].toString(),
                commentTime: setCommentTime(index),
                commentContent: this.studentCommentList[index]['content'],
              ),
            ),
          ),
        );
      }, childCount: studentCommentList.length),
    );
  }

  SliverToBoxAdapter _buildEmptyView() {
    return SliverToBoxAdapter(
      child: Container(
        width: 1024.w,
        height: 396.w,
        child: Stack(
          children: <Widget>[
            Padding(
              padding: EdgeInsets.only(left: 362.w, top: 105.w),
              child: Image.asset(
                'assets/images/yk_illustration_a@3x.png',
                width: 96.w,
                height: 96.w,
              ),
            ),
            Padding(
              padding: EdgeInsets.only(left: 493.w, top: 127.w),
              child: Text(
                '当前没有学生评论',
                style: TextStyle(
                    color: Color.fromRGBO(15, 32, 67, 1),
                    fontSize: 18.sp,
                    fontFamily: 'PingFangSC-Regular'),
              ),
            ),
            Padding(
              padding: EdgeInsets.only(left: 493.w, top: 156.w),
              child: Text(
                '邀请学生评论课程吧～',
                style: TextStyle(
                    color: Color.fromRGBO(155, 157, 161, 1),
                    fontSize: 12.sp,
                    fontFamily: 'PingFangSC-Regular'),
              ),
            ),
          ],
        ),
      ),
    );
  }

  String setCommentTime(int index) {
    String tempTime = studentCommentList[index]['updateDate'].toString();
    var finishedTime = DateTime.parse(tempTime);
    var currentTime = DateTime.now();
    String waitingTime =
        currentTime.difference(finishedTime).inHours.toString();
    return waitingTime.isNotEmpty ? waitingTime + '小时前' : '--小时前';
  }

  Future<void> _refresh() async {
    await Future<Null>.delayed(Duration(seconds: 0), () {
      setDataOfFinishedClass();

      return null;
    });
  }

  void setDataOfFinishedClass() {
    //网络请求
    FormData params = FormData.fromMap({
      'coursesCompleteId': this.widget.classId,
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
    DioManager.getInstance().get("/class/complete/score", params, (result) {
      setState(() {
        this.studentCommentList = result['data'];

        print(this.studentCommentList);
      });
      //验证通过提交数据
    }, (error) {});
    //
  }
}
