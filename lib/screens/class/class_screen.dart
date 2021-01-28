import 'dart:collection';

import 'package:Cloudgrain_teacher_teach/data/User.dart';
import 'package:Cloudgrain_teacher_teach/screens/class/class_establish.dart';
import 'package:Cloudgrain_teacher_teach/screens/class/class_showClass_screen.dart';
import 'package:event_bus/event_bus.dart';
import 'package:flutter_custom_calendar/flutter_custom_calendar.dart';
import 'package:flutter/material.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter_custom_calendar/utils/LogUtil.dart';

import 'package:flutter_screenutil/flutter_screenutil.dart';

class CloudClassScreen extends StatefulWidget {
  @override
  _CloudClassScreenState createState() => _CloudClassScreenState();
}

class DateInfo {
  String date;

  DateInfo(
    this.date,
  );
}

class _CloudClassScreenState extends State<CloudClassScreen>
    with SingleTickerProviderStateMixin {
  CalendarController controller;
  CalendarViewWidget calendar;
  HashSet<DateTime> _selectedDate = new HashSet();
  HashSet<DateModel> _selectedModels = new HashSet();

  GlobalKey<CalendarContainerState> _globalKey = new GlobalKey();

  String _selectDate;
  String _selectYear;
  String _selectMonth;
  String _selectDay;

  @override
  void initState() {
    _selectYear = DateTime.now().year.toString();
    _selectMonth = DateTime.now().month.toString();
    _selectDay = DateTime.now().day.toString();
    _selectedDate.add(DateTime.now());
    DateModel today = DateModel.fromDateTime(DateTime.now());

    _selectDate = _selectYear + '年' + _selectMonth + '月' + _selectDay + '日';

    controller = new CalendarController(
        minYear: 2019,
        minYearMonth: 1,
        maxYear: 2030,
        maxYearMonth: 12,
        showMode: CalendarConstants.MODE_SHOW_ONLY_MONTH,
        // selectedDateTimeList: _selectedDate,
        selectDateModel: today,
        selectMode: CalendarSelectedMode.singleSelect)
      ..addOnCalendarSelectListener((dateModel) {
        _selectedModels.add(dateModel);
        setState(() {
          _selectYear = dateModel.year.toString();
          _selectMonth = dateModel.month.toString();
          _selectDay = dateModel.day.toString();
          _selectDate =
              _selectYear + '年' + _selectMonth + '月' + _selectDay + '日';
          User.shared().classTagDay = DateTime(int.parse(_selectYear),
              int.parse(_selectMonth), int.parse(_selectDay));
          final info = DateInfo('change');
          User.shared().eventBus.fire(info);
        });
      })
      ..addOnCalendarUnSelectListener((dateModel) {
        LogUtil.log(
            TAG: '_selectedModels', message: _selectedModels.toString());
        LogUtil.log(TAG: 'dateModel', message: dateModel.toString());
        if (_selectedModels.contains(dateModel)) {
          _selectedModels.remove(dateModel);
        }
        setState(() {
          _selectDate = '';
        });
      });
    calendar = new CalendarViewWidget(
      key: _globalKey,
      calendarController: controller,
      dayWidgetBuilder: (DateModel model) {
        double wd = (MediaQuery.of(context).size.width - 20) / 7;
        //double wd = 21.w;
        bool _isSelected = model.isSelected;
        if (_isSelected &&
            CalendarSelectedMode.singleSelect ==
                controller.calendarConfiguration.selectMode) {
          _selectYear = model.year.toString();
          _selectMonth = model.month.toString();
          _selectDay = model.day.toString();
          _selectDate =
              _selectYear + '年' + _selectMonth + '月' + _selectDay + '日';
          User.shared().classTagDay = DateTime(int.parse(_selectYear),
              int.parse(_selectMonth), int.parse(_selectDay));
          final info = DateInfo('change');
          User.shared().eventBus.fire(info);
        }
        return ClipRRect(
          borderRadius: BorderRadius.all(Radius.circular(wd / 2)),
          child: Container(
            color: _isSelected ? Color.fromRGBO(0, 145, 255, 1) : Colors.white,
            alignment: Alignment.center,
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.center,
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                Text(
                  model.day.toString(),
                  style: TextStyle(
                      color: model.isCurrentMonth
                          ? (_isSelected == false
                              ? (model.isWeekend
                                  ? Colors.black38
                                  : Colors.black87)
                              : Colors.white)
                          : Colors.black38),
                ),
//              Text(model.lunarDay.toString()),
              ],
            ),
          ),
        );
      },
    );
    WidgetsBinding.instance.addPostFrameCallback((timeStamp) {
      controller.addExpandChangeListener((value) {
        /// 添加改变 月视图和 周视图的监听
        //_isMonthSelected = value;
      });
    });
    super.initState();
    // controller.moveToCalendar(
    //     int.parse(_selectYear), int.parse(_selectMonth), int.parse(_selectDay));
  }

  @override
  void dispose() {
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    ScreenUtil.init(context,
        designSize: Size(1024, 768), allowFontScaling: false);
    return Scaffold(
        backgroundColor: Colors.white,
        body: Stack(
          children: <Widget>[
            Padding(
              padding: EdgeInsets.only(left: 350.w),
              child: ClassShowClassScreen(),
            ),
            Padding(
              padding: EdgeInsets.only(left: 0.w, top: 0.w),
              child: setCalenderView(),
            )
          ],
        ));
  }

  Widget setCalenderView() {
    return Container(
        width: 350.w,
        height: 768.h,
        decoration:
            BoxDecoration(color: Color.fromRGBO(255, 255, 255, 1), boxShadow: [
          BoxShadow(
              color: Color.fromRGBO(195, 195, 195, 0.5),
              offset: Offset(0, 2.w), //阴影xy轴偏移量
              blurRadius: 7.w, //阴影模糊程度
              spreadRadius: 0 //阴影扩散程度
              )
        ]),
        child: CustomScrollView(
          slivers: <Widget>[
            SliverToBoxAdapter(
              child: Container(
                height: 768.w,
                margin: EdgeInsets.fromLTRB(16.w, 40.w, 16.w, 10.w),
                decoration: BoxDecoration(
                    color: Color.fromRGBO(255, 255, 255, 1),
                    //color: Colors.red,
                    borderRadius: BorderRadius.circular(7.w),
                    boxShadow: [
                      BoxShadow(
                          color: Color.fromRGBO(180, 180, 180, 0.11),
                          offset: Offset(0, 2.w), //阴影xy轴偏移量
                          blurRadius: 9.w, //阴影模糊程度
                          spreadRadius: 0

                          ///阴影扩散程度
                          )
                    ]),
                child: Column(
                  children: <Widget>[
                    Container(
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: <Widget>[
                          IconButton(
                              icon: Image.asset(
                                'assets/icons/yk_icon_left@3x.png',
                                width: 28.0,
                                height: 28.0,
                              ),
                              color: Colors.red,
                              splashColor: Colors.transparent,
                              highlightColor: Colors.transparent,
                              onPressed: () {
                                controller.previousPage();
                              }),
                          Text(
                            _selectDate,
                          ),
                          IconButton(
                              icon: Image.asset(
                                'assets/icons/yk_icon_right@3x.png',
                                width: 28.0,
                                height: 28.0,
                              ),
                              color: Colors.red,
                              splashColor: Colors.transparent,
                              highlightColor: Colors.transparent,
                              onPressed: () {
                                controller.nextPage();
                              }),
                        ],
                      ),
                    ),
                    Container(
                      child: Stack(
                        children: <Widget>[
                          Padding(
                            padding: EdgeInsets.only(left: 0.w, top: 0.w),
                            child: calendar,
                          ),
                          Padding(
                            padding: EdgeInsets.only(
                                left: 2.w, top: 400.w, right: 2.w),
                            child: Container(
                              width: 322.w,
                              height: 44.w,
                              child: MaterialButton(
                                  child: Text(
                                    "开课",
                                    style: TextStyle(
                                      color: Color.fromRGBO(255, 255, 255, 1),
                                      fontFamily: 'PingFangSC-Semibold',
                                      fontSize: 16.sp,
                                    ),
                                  ),
                                  color: Color.fromRGBO(0, 145, 255, 1),
                                  shape: RoundedRectangleBorder(
                                      borderRadius: BorderRadius.circular(6.w)),
                                  //borderSide: BorderSide(color: Colors.orange, width: 1),
                                  onPressed: () {
                                    Navigator.of(context).push(
                                        CupertinoPageRoute(
                                            builder: (context) =>
                                                ClassEstablishScreen()));
                                  }),
                            ),
                          )
                        ],
                      ),
                    ),
                  ],
                ),
                //child: calendar,
              ),
            ),
            // SliverToBoxAdapter(
            //   child: Padding(
            //     padding: EdgeInsets.only(left: 17.w, top: 0.w, right: 17.w),
            //     child: Container(
            //       width: 322.w,
            //       height: 44.w,
            //       child: MaterialButton(
            //           child: Text(
            //             "开课",
            //             style: TextStyle(
            //               color: Color.fromRGBO(255, 255, 255, 1),
            //               fontFamily: 'PingFangSC-Semibold',
            //               fontSize: 16.sp,
            //             ),
            //           ),
            //           color: Color.fromRGBO(0, 145, 255, 1),
            //           shape: RoundedRectangleBorder(
            //               borderRadius: BorderRadius.circular(6.w)),
            //           //borderSide: BorderSide(color: Colors.orange, width: 1),
            //           onPressed: () {
            //             Navigator.of(context).push(CupertinoPageRoute(
            //                 builder: (context) => ClassEstablishScreen()));
            //           }),
            //     ),
            //   ),
            // )
          ],
        ));
  }

  Future<void> _refresh() async {
    await Future<Null>.delayed(Duration(seconds: 0), () {
      print('刷新');
      setState(() {});
      return null;
    });
  }
}
