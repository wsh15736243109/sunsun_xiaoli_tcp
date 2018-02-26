package sunsun.xiaoli.jiarebang.utils;

import android.text.Html;
import android.text.Spanned;

import org.apache.commons.lang.StringEscapeUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sunsun.xiaoli.jiarebang.R;

/**
 * 表情转 字符串
 * 
 * @author Young
 * 
 */
public class Spanned2String {

	// <p dir="ltr"><img src="2130837782"><img src="2130837783"></p>
	// <p dir="ltr">hello</p>

	static Pattern pattern;
	static Map<String, String> map = new HashMap<>();
	static {
		pattern = Pattern.compile("<img src=\"\\d+\">");

		map.put(String.valueOf(R.drawable.f000), "[:f000}");
		map.put(String.valueOf(R.drawable.f001), "[:f001}");
		map.put(String.valueOf(R.drawable.f002), "[:f002}");
		map.put(String.valueOf(R.drawable.f003), "[:f003}");
		map.put(String.valueOf(R.drawable.f004), "[:f004}");
		map.put(String.valueOf(R.drawable.f005), "[:f005}");
		map.put(String.valueOf(R.drawable.f006), "[:f006}");
		map.put(String.valueOf(R.drawable.f007), "[:f007}");
		map.put(String.valueOf(R.drawable.f008), "[:f008}");
		map.put(String.valueOf(R.drawable.f009), "[:f009}");
		map.put(String.valueOf(R.drawable.f010), "[:f010}");
		map.put(String.valueOf(R.drawable.f011), "[:f011}");
		map.put(String.valueOf(R.drawable.f012), "[:f012}");
		map.put(String.valueOf(R.drawable.f013), "[:f013}");
		map.put(String.valueOf(R.drawable.f014), "[:f014}");
		map.put(String.valueOf(R.drawable.f015), "[:f015}");
		map.put(String.valueOf(R.drawable.f016), "[:f016}");
		map.put(String.valueOf(R.drawable.f017), "[:f017}");
		map.put(String.valueOf(R.drawable.f018), "[:f018}");
		map.put(String.valueOf(R.drawable.f019), "[:f019}");
		map.put(String.valueOf(R.drawable.f020), "[:f020}");
		map.put(String.valueOf(R.drawable.f021), "[:f021}");
		map.put(String.valueOf(R.drawable.f022), "[:f022}");
		map.put(String.valueOf(R.drawable.f023), "[:f023}");
		map.put(String.valueOf(R.drawable.f024), "[:f024}");
		map.put(String.valueOf(R.drawable.f025), "[:f025}");
		map.put(String.valueOf(R.drawable.f026), "[:f026}");
		map.put(String.valueOf(R.drawable.f027), "[:f027}");
		map.put(String.valueOf(R.drawable.f028), "[:f028}");
		map.put(String.valueOf(R.drawable.f029), "[:f029}");
		map.put(String.valueOf(R.drawable.f030), "[:f030}");
		map.put(String.valueOf(R.drawable.f031), "[:f031}");
		map.put(String.valueOf(R.drawable.f032), "[:f032}");
		map.put(String.valueOf(R.drawable.f033), "[:f033}");
		map.put(String.valueOf(R.drawable.f034), "[:f034}");
		map.put(String.valueOf(R.drawable.f035), "[:f035}");
		map.put(String.valueOf(R.drawable.f036), "[:f036}");
		map.put(String.valueOf(R.drawable.f037), "[:f037}");
		map.put(String.valueOf(R.drawable.f038), "[:f038}");
		map.put(String.valueOf(R.drawable.f039), "[:f039}");
		map.put(String.valueOf(R.drawable.f040), "[:f040}");
		map.put(String.valueOf(R.drawable.f041), "[:f041}");
		map.put(String.valueOf(R.drawable.f042), "[:f042}");
		map.put(String.valueOf(R.drawable.f043), "[:f043}");
		map.put(String.valueOf(R.drawable.f044), "[:f044}");
		map.put(String.valueOf(R.drawable.f045), "[:f045}");
		map.put(String.valueOf(R.drawable.f046), "[:f046}");
		map.put(String.valueOf(R.drawable.f047), "[:f047}");
		map.put(String.valueOf(R.drawable.f048), "[:f048}");
		map.put(String.valueOf(R.drawable.f049), "[:f049}");
		map.put(String.valueOf(R.drawable.f050), "[:f050}");
		map.put(String.valueOf(R.drawable.f051), "[:f051}");
		map.put(String.valueOf(R.drawable.f052), "[:f052}");
		map.put(String.valueOf(R.drawable.f053), "[:f053}");
		map.put(String.valueOf(R.drawable.f054), "[:f054}");
		map.put(String.valueOf(R.drawable.f055), "[:f055}");
		map.put(String.valueOf(R.drawable.f056), "[:f056}");
		map.put(String.valueOf(R.drawable.f057), "[:f057}");
		map.put(String.valueOf(R.drawable.f058), "[:f058}");
		map.put(String.valueOf(R.drawable.f059), "[:f059}");
		map.put(String.valueOf(R.drawable.f060), "[:f060}");
		map.put(String.valueOf(R.drawable.f061), "[:f061}");
		map.put(String.valueOf(R.drawable.f062), "[:f062}");
		map.put(String.valueOf(R.drawable.f063), "[:f063}");
		map.put(String.valueOf(R.drawable.f064), "[:f064}");
		map.put(String.valueOf(R.drawable.f065), "[:f065}");
		map.put(String.valueOf(R.drawable.f066), "[:f066}");
		map.put(String.valueOf(R.drawable.f067), "[:f067}");
		map.put(String.valueOf(R.drawable.f068), "[:f068}");
		map.put(String.valueOf(R.drawable.f069), "[:f069}");
		map.put(String.valueOf(R.drawable.f070), "[:f070}");
		map.put(String.valueOf(R.drawable.f071), "[:f071}");
		map.put(String.valueOf(R.drawable.f072), "[:f072}");
		map.put(String.valueOf(R.drawable.f073), "[:f073}");
		map.put(String.valueOf(R.drawable.f074), "[:f074}");
		map.put(String.valueOf(R.drawable.f075), "[:f075}");
		map.put(String.valueOf(R.drawable.f076), "[:f076}");
		map.put(String.valueOf(R.drawable.f077), "[:f077}");
		map.put(String.valueOf(R.drawable.f078), "[:f078}");
		map.put(String.valueOf(R.drawable.f079), "[:f079}");
		map.put(String.valueOf(R.drawable.f080), "[:f080}");
		map.put(String.valueOf(R.drawable.f081), "[:f081}");
		map.put(String.valueOf(R.drawable.f082), "[:f082}");
		map.put(String.valueOf(R.drawable.f083), "[:f083}");
		map.put(String.valueOf(R.drawable.f084), "[:f084}");
		map.put(String.valueOf(R.drawable.f085), "[:f085}");
		map.put(String.valueOf(R.drawable.f086), "[:f086}");
		map.put(String.valueOf(R.drawable.f087), "[:f087}");
		map.put(String.valueOf(R.drawable.f088), "[:f088}");
		map.put(String.valueOf(R.drawable.f089), "[:f089}");
		map.put(String.valueOf(R.drawable.f090), "[:f090}");
		map.put(String.valueOf(R.drawable.f091), "[:f091}");

	}

	public static String decode(String str) {
		String[] tmp = str.split(";&#|&#|;");
		StringBuilder sb = new StringBuilder("");
		for (int i = 0; i < tmp.length; i++) {
			if (tmp[i].matches("\\d{5}")) {
				sb.append((char) Integer.parseInt(tmp[i]));
			} else {
				sb.append(tmp[i]);
			}
		}
		return sb.toString();
	}

	public static String parse(Spanned spanned) {

		try {

			String origHtml = Html.toHtml(spanned);
			// 模拟器： <p dir="ltr">&#24930;&#24930;</p>
			// 小米2A <p dir=ltr>&#24930;&#24930;</p>
			String origStr = "";
			if (origHtml.length() >= 5)
				origStr = origHtml.substring(origHtml.indexOf(">") + 1,
						origHtml.length() - 5);
			origStr = StringEscapeUtils.unescapeHtml(origStr);// html 汉字实体编码转换
			Matcher matcher = pattern.matcher(origStr);

			// <img src="2130837782">hello<img src="2130837783">
			StringBuilder stringBuilder = new StringBuilder();
			int last = 0;
			while (matcher.find()) {
				int s = matcher.start();
				int e = matcher.end();

				stringBuilder.append(origStr.substring(last, s));

				String group = matcher.group();
				String id = group.substring(10, group.length() - 2);

				String emojStr = map.get(id);
				if (emojStr == null) {
					stringBuilder.append(group);
				} else {
					stringBuilder.append(emojStr);

				}

				last = e;

			}
			stringBuilder.append(origStr.substring(last, origStr.length()));
			return stringBuilder.toString().replace("<br>", "\n");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return "";
		}

	}

}
