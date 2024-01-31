//横軸を作る
document.write('<tr>');
document.write('<th>目標日</th><th>完了ページ数</th><th>完了日</th>');

//縦の列を作る
document.write('<tr>');

//jの列を記述するため、1から9の数字を繰り返しHTMLに記述する
for (var tableElem = startingDate; column <= estimatedcompletion_date; column++) {
	document.write('<th>' + '<%=request.getAttribute("startingDate") %>'  + 'y</th>');

	//j*kを繰り返すことで、計算結果をtdタグの中に表示する
	for (var k = 1; k <= 9; k++) {
		document.write('<td>' + j * k + '</td>');
	}
	//ここでjの行を閉じる
	document.write('</tr>');
}