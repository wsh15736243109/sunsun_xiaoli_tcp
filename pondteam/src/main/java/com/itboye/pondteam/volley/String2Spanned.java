package com.itboye.pondteam.volley;

import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spanned;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.itboye.pondteam.app.MyApplication;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串转表情Spanned 表情格式 [:f000}
 * 
 * @author Young
 * 
 */
public class String2Spanned extends TypeAdapter<Spanned> {

	static Pattern pattern;

	static Map<String, Integer> map = new HashMap<String, Integer>();

	static ImageGetter imageGetter = new ImageGetter() {
		@Override
		public Drawable getDrawable(String source) {
			int id = Integer.parseInt(source);
			Drawable d = MyApplication.getInstance().getResources()
					.getDrawable(id);
			d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
			return d;
		}
	};

	static {

		pattern = Pattern.compile("\\[:f\\d{3}\\}");
//		map.put("[:f000}", R.drawable.f000);
//		map.put("[:f001}", R.drawable.f001);
//		map.put("[:f002}", R.drawable.f002);
//		map.put("[:f003}", R.drawable.f003);
//		map.put("[:f004}", R.drawable.f004);
//		map.put("[:f005}", R.drawable.f005);
//		map.put("[:f006}", R.drawable.f006);
//		map.put("[:f007}", R.drawable.f007);
//		map.put("[:f008}", R.drawable.f008);
//		map.put("[:f009}", R.drawable.f009);
//		map.put("[:f010}", R.drawable.f010);
//		map.put("[:f011}", R.drawable.f011);
//		map.put("[:f012}", R.drawable.f012);
//		map.put("[:f013}", R.drawable.f013);
//		map.put("[:f014}", R.drawable.f014);
//		map.put("[:f015}", R.drawable.f015);
//		map.put("[:f016}", R.drawable.f016);
//		map.put("[:f017}", R.drawable.f017);
//		map.put("[:f018}", R.drawable.f018);
//		map.put("[:f019}", R.drawable.f019);
//		map.put("[:f020}", R.drawable.f020);
//		map.put("[:f021}", R.drawable.f021);
//		map.put("[:f022}", R.drawable.f022);
//		map.put("[:f023}", R.drawable.f023);
//		map.put("[:f024}", R.drawable.f024);
//		map.put("[:f025}", R.drawable.f025);
//		map.put("[:f026}", R.drawable.f026);
//		map.put("[:f027}", R.drawable.f027);
//		map.put("[:f028}", R.drawable.f028);
//		map.put("[:f029}", R.drawable.f029);
//		map.put("[:f030}", R.drawable.f030);
//		map.put("[:f031}", R.drawable.f031);
//		map.put("[:f032}", R.drawable.f032);
//		map.put("[:f033}", R.drawable.f033);
//		map.put("[:f034}", R.drawable.f034);
//		map.put("[:f035}", R.drawable.f035);
//		map.put("[:f036}", R.drawable.f036);
//		map.put("[:f037}", R.drawable.f037);
//		map.put("[:f038}", R.drawable.f038);
//		map.put("[:f039}", R.drawable.f039);
//		map.put("[:f040}", R.drawable.f040);
//		map.put("[:f041}", R.drawable.f041);
//		map.put("[:f042}", R.drawable.f042);
//		map.put("[:f043}", R.drawable.f043);
//		map.put("[:f044}", R.drawable.f044);
//		map.put("[:f045}", R.drawable.f045);
//		map.put("[:f046}", R.drawable.f046);
//		map.put("[:f047}", R.drawable.f047);
//		map.put("[:f048}", R.drawable.f048);
//		map.put("[:f049}", R.drawable.f049);
//		map.put("[:f050}", R.drawable.f050);
//		map.put("[:f051}", R.drawable.f051);
//		map.put("[:f052}", R.drawable.f052);
//		map.put("[:f053}", R.drawable.f053);
//		map.put("[:f054}", R.drawable.f054);
//		map.put("[:f055}", R.drawable.f055);
//		map.put("[:f056}", R.drawable.f056);
//		map.put("[:f057}", R.drawable.f057);
//		map.put("[:f058}", R.drawable.f058);
//		map.put("[:f059}", R.drawable.f059);
//		map.put("[:f060}", R.drawable.f060);
//		map.put("[:f061}", R.drawable.f061);
//		map.put("[:f062}", R.drawable.f062);
//		map.put("[:f063}", R.drawable.f063);
//		map.put("[:f064}", R.drawable.f064);
//		map.put("[:f065}", R.drawable.f065);
//		map.put("[:f066}", R.drawable.f066);
//		map.put("[:f067}", R.drawable.f067);
//		map.put("[:f068}", R.drawable.f068);
//		map.put("[:f069}", R.drawable.f069);
//		map.put("[:f070}", R.drawable.f070);
//		map.put("[:f071}", R.drawable.f071);
//		map.put("[:f072}", R.drawable.f072);
//		map.put("[:f073}", R.drawable.f073);
//		map.put("[:f074}", R.drawable.f074);
//		map.put("[:f075}", R.drawable.f075);
//		map.put("[:f076}", R.drawable.f076);
//		map.put("[:f077}", R.drawable.f077);
//		map.put("[:f078}", R.drawable.f078);
//		map.put("[:f079}", R.drawable.f079);
//		map.put("[:f080}", R.drawable.f080);
//		map.put("[:f081}", R.drawable.f081);
//		map.put("[:f082}", R.drawable.f082);
//		map.put("[:f083}", R.drawable.f083);
//		map.put("[:f084}", R.drawable.f084);
//		map.put("[:f085}", R.drawable.f085);
//		map.put("[:f086}", R.drawable.f086);
//		map.put("[:f087}", R.drawable.f087);
//		map.put("[:f088}", R.drawable.f088);
//		map.put("[:f089}", R.drawable.f089);
//		map.put("[:f090}", R.drawable.f090);
//		map.put("[:f091}", R.drawable.f091);
//		// 自定义表情
//		map.put("[:f100}", R.drawable.f100);
//		map.put("[:f101}", R.drawable.f101);
//		map.put("[:f102}", R.drawable.f102);
//		map.put("[:f103}", R.drawable.f103);
//		map.put("[:f104}", R.drawable.f104);
//		map.put("[:f105}", R.drawable.f105);
//		map.put("[:f106}", R.drawable.f106);
//		map.put("[:f107}", R.drawable.f107);
	}

	@Override
	public Spanned read(JsonReader in) throws IOException {
		if (in.peek() == JsonToken.NULL) {
			in.nextNull();
			return null;
		}

		String origStr = in.nextString();
		Matcher matcher = pattern.matcher(origStr);

		StringBuilder stringBuilder = new StringBuilder();

		int last = 0;
		while (matcher.find()) {

			int s = matcher.start();
			int e = matcher.end();
			stringBuilder.append(origStr.substring(last, s));

			String group = matcher.group();
			Integer emojId = map.get(group);
			if (emojId == null) {
				stringBuilder.append(group);
			} else {
				stringBuilder.append("<img src='");
				stringBuilder.append(emojId);
				stringBuilder.append("'/>");
			}

			last = e;

		}
		stringBuilder.append(origStr.substring(last, origStr.length()));

		// String ss = "<img src='" + R.drawable.ic_launcher + "'/>";
		//

		//
		return Html.fromHtml(stringBuilder.toString(), imageGetter, null);
	}

	@Override
	public void write(JsonWriter arg0, Spanned arg1) throws IOException {

	}

}
