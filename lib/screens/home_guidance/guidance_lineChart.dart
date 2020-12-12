import 'package:fl_chart/fl_chart.dart';
import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';

class GuidanceLineChart extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => GuidanceLineChartState();
}

class GuidanceLineChartState extends State<GuidanceLineChart> {
  bool isShowingMainData;

  @override
  void initState() {
    super.initState();
    isShowingMainData = false;
  }

  @override
  Widget build(BuildContext context) {
    ScreenUtil.init(context,
        designSize: Size(1024, 768), allowFontScaling: false);
    return AspectRatio(
      aspectRatio: 1.23,
      child: Container(
        decoration: BoxDecoration(
          borderRadius: BorderRadius.all(Radius.circular(7.w)),
          color: Colors.white,
        ),
        child: Stack(
          children: <Widget>[
            Column(
              crossAxisAlignment: CrossAxisAlignment.stretch,
              children: <Widget>[
                Container(),
                Expanded(
                  child: Padding(
                    padding: EdgeInsets.only(right: 15.w, left: 15.w),
                    child: LineChart(
                      sampleData(),
                      // isShowingMainData ? sampleData1() : sampleData2(),
                      // swapAnimationDuration: const Duration(milliseconds: 250),
                    ),
                  ),
                ),
                const SizedBox(
                  height: 10,
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }

  LineChartData sampleData() {
    return LineChartData(
      lineTouchData: LineTouchData(
        enabled: false,
      ),
      gridData: FlGridData(
        show: false,
      ),
      titlesData: FlTitlesData(
        bottomTitles: SideTitles(
          showTitles: true,
          reservedSize: 22,
          getTextStyles: (value) => const TextStyle(
            color: Color.fromRGBO(163, 169, 175, 1),
            fontWeight: FontWeight.normal,
            fontSize: 11,
          ),
          margin: 10,
          getTitles: (value) {
            switch (value.toInt()) {
              case 1:
                return '28日';
              case 1:
                return '29日';
              case 2:
                return '30日';
              case 3:
                return '10.1日';
              case 4:
                return '2日';
              case 5:
                return '2日';
              case 6:
                return '3日';
              case 7:
                return '4日';
              case 8:
                return '5日';
              case 9:
                return '6日';
            }
            return '';
          },
        ),
        leftTitles: SideTitles(
          showTitles: false,
          getTextStyles: (value) => const TextStyle(
            color: Color.fromRGBO(163, 169, 175, 1),
            fontWeight: FontWeight.normal,
            fontSize: 11,
          ),
          getTitles: (value) {
            switch (value.toInt()) {
              case 1:
                return '';
              case 2:
                return '';
              case 3:
                return '3m';
              case 4:
                return '';
              case 5:
                return '';
              case 6:
                return '6m';
            }
            return '';
          },
          margin: 0,
          reservedSize: 30,
        ),
      ),
      borderData: FlBorderData(
          show: true,
          border: const Border(
            bottom: BorderSide(
              color: Color.fromRGBO(168, 211, 198, 1),
              width: 1,
            ),
            left: BorderSide(
              color: Colors.transparent,
            ),
            right: BorderSide(
              color: Colors.transparent,
            ),
            top: BorderSide(
              color: Colors.transparent,
            ),
          )),
      minX: 1,
      maxX: 9,
      maxY: 10,
      minY: 0,
      lineBarsData: linesBarData2(),
    );
  }

  List<LineChartBarData> linesBarData2() {
    return [
      //折线
      LineChartBarData(
        spots: [
          FlSpot(1, 3.8),
          FlSpot(2, 1.9),
          FlSpot(3, 5),
          FlSpot(4, 3.3),
          FlSpot(5, 7.5),
          FlSpot(6, 5.5),
          FlSpot(7, 1.5),
          FlSpot(8, 2.5),
          FlSpot(9, 0.5),
        ],
        isCurved: true,
        curveSmoothness: 0,
        colors: const [
          Color.fromRGBO(168, 211, 198, 0.3),
        ],
        barWidth: 1,
        isStrokeCapRound: true,
        dotData: FlDotData(show: true),
        belowBarData: BarAreaData(show: true, colors: [
          const Color.fromRGBO(168, 211, 198, 0.15),
        ]),
      ),
    ];
  }
}
