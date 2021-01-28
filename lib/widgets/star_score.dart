import 'package:flutter/material.dart';

class StarScore extends StatelessWidget {
  final double setHeight;
  final int score;

  const StarScore({Key key, this.score, this.setHeight}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      width: 86 * setHeight / 14,
      height: setHeight,
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: <Widget>[
          Image.asset(
            score >= 1
                ? 'assets/icons/icon_stars_yellow@3x.png'
                : 'assets/icons/icon_stars_grey@3x.png',
            width: setHeight,
            height: setHeight,
          ),
          Image.asset(
            score >= 2
                ? 'assets/icons/icon_stars_yellow@3x.png'
                : 'assets/icons/icon_stars_grey@3x.png',
            width: setHeight,
            height: setHeight,
          ),
          Image.asset(
            score >= 3
                ? 'assets/icons/icon_stars_yellow@3x.png'
                : 'assets/icons/icon_stars_grey@3x.png',
            width: setHeight,
            height: setHeight,
          ),
          Image.asset(
            score >= 4
                ? 'assets/icons/icon_stars_yellow@3x.png'
                : 'assets/icons/icon_stars_grey@3x.png',
            width: setHeight,
            height: setHeight,
          ),
          Image.asset(
            score == 5
                ? 'assets/icons/icon_stars_yellow@3x.png'
                : 'assets/icons/icon_stars_grey@3x.png',
            width: setHeight,
            height: setHeight,
          )
        ],
      ),
    );
  }
}
