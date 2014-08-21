package com.example.lifehelp_main.calculator;

import java.util.ArrayList;
import java.util.List;

import com.example.lifehelp_main.R;
import android.app.Activity;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CalculatorActivity extends Activity implements OnClickListener {

	public static final String OPERATOR_ADD = "+";
	public static final String OPERATOR_SUB = "-";
	public static final String OPERATOR_MUL = "×";
	public static final String OPERATOR_DIV = "÷";
	public static final String OPERATOR_LEFT_PARENTHESIS = "(";
	public static final String OPERATOR_RIGHT_PARENTHESIS = ")";
	public static final int MAX_LENTH = 13;
	private Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9,
			btn_add, btn_sub, btn_mul, btn_div, btn_clear, btn_back, btn_point,
			btn_left, btn_right, btn_result;
	private Vibrator vibrator = null;
	private boolean ifVibrate = false;
	private EditText textView;
	private String context;
	private TextView historyTV;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉标题栏
		setContentView(R.layout.calculator_main);
		textView = (EditText) findViewById(R.id.showEditText);
		historyTV = (TextView) findViewById(R.id.result_calculator);
		btn0 = (Button) findViewById(R.id.btn0);
		btn0.setOnClickListener(this);
		btn1 = (Button) findViewById(R.id.btn1);
		btn1.setOnClickListener(this);
		btn2 = (Button) findViewById(R.id.btn2);
		btn2.setOnClickListener(this);
		btn3 = (Button) findViewById(R.id.btn3);
		btn3.setOnClickListener(this);
		btn4 = (Button) findViewById(R.id.btn4);
		btn4.setOnClickListener(this);
		btn5 = (Button) findViewById(R.id.btn5);
		btn5.setOnClickListener(this);
		btn6 = (Button) findViewById(R.id.btn6);
		btn6.setOnClickListener(this);
		btn7 = (Button) findViewById(R.id.btn7);
		btn7.setOnClickListener(this);
		btn8 = (Button) findViewById(R.id.btn8);
		btn8.setOnClickListener(this);
		btn9 = (Button) findViewById(R.id.btn9);
		btn9.setOnClickListener(this);
		btn_add = (Button) findViewById(R.id.btn_add);
		btn_add.setOnClickListener(this);
		btn_sub = (Button) findViewById(R.id.btn_sub);
		btn_sub.setOnClickListener(this);
		btn_mul = (Button) findViewById(R.id.btn_mul);
		btn_mul.setOnClickListener(this);
		btn_div = (Button) findViewById(R.id.btn_div);
		btn_div.setOnClickListener(this);
		btn_clear = (Button) findViewById(R.id.btn_clear);
		btn_clear.setOnClickListener(this);
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		btn_point = (Button) findViewById(R.id.btn_point);
		btn_point.setOnClickListener(this);
		btn_left = (Button) findViewById(R.id.btn_left);
		btn_left.setOnClickListener(this);
		btn_right = (Button) findViewById(R.id.btn_right);
		btn_right.setOnClickListener(this);
		btn_result = (Button) findViewById(R.id.btn_result);
		btn_result.setOnClickListener(this);
		// et.setInputType(InputType.TYPE_NULL);
		// -----------组织软键盘的弹出-----------
		textView.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				int inType = textView.getInputType(); // backup the input type
				textView.setInputType(InputType.TYPE_NULL); // disable soft
															// input
				textView.onTouchEvent(event); // call native handler
				textView.setInputType(inType); // restore input type
				textView.setSelection(textView.getText().length());
				return true;

			}

		});
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		context = textView.getText().toString();
		if (context.equals("Infinity") || context.equals("出错啦")) {
			textView.setText("");
		}
		context = textView.getText().toString();
		// 获取Vibrate对象
		// 设置Vibrate的震动周期
		if (ifVibrate) {
			vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(17);
		}
		switch (v.getId()) {
		case R.id.btn0:
			appendNum("0");
			break;
		case R.id.btn1:
			appendNum("1");
			break;
		case R.id.btn2:
			appendNum("2");
			break;
		case R.id.btn3:
			appendNum("3");
			break;
		case R.id.btn4:
			appendNum("4");
			break;
		case R.id.btn5:
			appendNum("5");
			break;
		case R.id.btn6:
			appendNum("6");
			break;
		case R.id.btn7:
			appendNum("7");
			break;
		case R.id.btn8:
			appendNum("8");
			break;
		case R.id.btn9:
			appendNum("9");
			break;
		case R.id.btn_add:
			operatorAppend(OPERATOR_ADD);
			break;
		case R.id.btn_sub:

			operatorAppend(OPERATOR_SUB);
			break;
		case R.id.btn_mul:
			operatorAppend(OPERATOR_MUL);
			break;
		case R.id.btn_div:
			operatorAppend(OPERATOR_DIV);
			break;

		case R.id.btn_point:
			context = textView.getText().toString();
			int i = context.lastIndexOf(".");
			int j = context.lastIndexOf(OPERATOR_ADD);
			int k = context.lastIndexOf(OPERATOR_SUB);
			int m = context.lastIndexOf(OPERATOR_MUL);
			int n = context.lastIndexOf(OPERATOR_DIV);
			if (i < j | i < k | i < m | i < n | i == -1) {
				if (context.equals("") || context.endsWith(OPERATOR_SUB)
						|| context.endsWith(OPERATOR_MUL)
						|| context.endsWith(OPERATOR_DIV)
						|| context.endsWith(OPERATOR_ADD)
						|| context.endsWith("(") || context.endsWith(")")) {
					textView.append("0.");
				} else {
					textView.append(".");
				}
			}
			break;
		case R.id.btn_left:
			if (isresult) {
				textView.setText("");
				isresult = false;
			}
			textView.append("(");

			break;
		case R.id.btn_right:
			ifCanAppendRight();
			break;
		case R.id.btn_clear:
			historyTV.setText("");
			textView.setText("0");
			textView.setSelection(1);
			list.clear();
			break;
		case R.id.btn_back:
			if (TextUtils.isEmpty(textView.getText().toString())) {
				break;
			}
			if (isresult) {
				textView.setText("");
				isresult = false;
				break;
			}
			textView.setText(context.substring(0, context.length() - 1));
			textView.setSelection(context.length() - 1);
			break;
		case R.id.btn_result:
			if (TextUtils.isEmpty(textView.getText().toString())) {
				break;
			}
			String tagStr = textView.getText().toString();
			if (!hasOperator(tagStr)) {
				// textView.setText("");
				// list.clear();
				return;
			}
			addList();
			System.out.println("size=" + list.size());
			hasMistake();
			if (mistake) {
				Toast.makeText(this, "你的算式有误~", Toast.LENGTH_SHORT).show();
				list.clear();
				mistake = false;
				break;
			}
			try {
				operation();
			} catch (NumberFormatException e) {
				historyTV.setText("");
				textView.setText("出错啦");
				textView.setSelection(textView.getText().toString().length());
				list.clear();

				break;
			}
			dealResult();

			textView.setText(list.get(0));
			historyTV.setText(tagStr + "=");
			textView.setSelection(textView.getText().toString().length());
			list.clear();
			isresult = true;
			break;
		}
	}

	private boolean hasOperator(String str) {
		if (str.contains(OPERATOR_ADD) || str.contains(OPERATOR_SUB)
				|| str.contains(OPERATOR_MUL) || str.contains(OPERATOR_DIV)
				|| str.contains("(") || str.contains(")")) {
			return true;
		}
		return false;
	}

	private boolean isresult = false;

	private void ifCanAppendRight() {
		// TODO Auto-generated method stub
		context = textView.getText().toString();
		if (context.equals("")) {
			return;
		}
		if (context.endsWith(OPERATOR_ADD) || context.endsWith(OPERATOR_SUB)
				|| context.endsWith(OPERATOR_MUL)
				|| context.endsWith(OPERATOR_DIV) || context.endsWith("(")) {
			return;
		}
		// 如果et中(的个数 <= )的个数 不允许输入
		int num1 = 0, num2 = 0;
		char[] ch = context.toCharArray();
		for (char c : ch) {
			if (c == '(')
				num1++;
			if (c == ')')
				num2++;
		}
		if (num1 <= num2) {
			return;
		}
		textView.append(")");
	}

	public void operatorAppend(String str) {

		if (context.equals("")) {
			if (str.equals(OPERATOR_SUB)) {
				textView.append(str);
			}
			return;
		}
		char c = context.charAt(context.length() - 1);
		// 如果et只有"-" 且按 - 时 ，将et设为空
		if (context.length() == 1 && c == '-') {
			textView.setText("");
			return;
		}
		// 如果输入运算符之前为"("时，只允许 - 输入
		if (c == '(') {
			if (str.equals(OPERATOR_SUB)) {
				textView.append(str);
				return;
			} else {
				return;
			}
		}
		// 如果et是以(- 结尾时 不允许输入
		if (context.length() > 1
				&& context.substring(context.length() - 2, context.length())
						.equals("(" + OPERATOR_SUB)) {
			if (str.equals(OPERATOR_SUB)) {
				textView.setText(context.substring(0, context.length() - 1));
				textView.setSelection(textView.getText().toString().length());
				return;
			} else {
				return;
			}
		}
		if (c == '+' || c == '-' || c == '×' || c == '÷') {
			textView.setText(context.substring(0, context.length() - 1) + str);
			textView.setSelection(context.length());
		} else {
			textView.append(str);
		}

		isresult = false;
	}

	public void appendNum(String num) {
		context = textView.getText().toString();
		// 判断是否刚做完一次运算，如果是则先将textView清空
		if (isresult) {
			textView.setText("");
			isresult = false;
		}

		isOnlyZero();

		textView.append(num);
	}

	public void isOnlyZero() {
		context = textView.getText().toString();
		if (context.equals("0")) {
			textView.setText("");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0, 1, 1, "打开震动");
		menu.add(0, 2, 2, "关闭震动");
		menu.add(1, 3, 3, "关        于");
		menu.add(1, 4, 4, "退        出");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case 1:
			ifVibrate = true;
			break;
		case 2:
			ifVibrate = false;
			break;
		case 3:
			Toast.makeText(this, "自制简易计算器", Toast.LENGTH_SHORT).show();
			break;
		case 4:
			finish();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	List<String> list = new ArrayList<String>();

	public void addList() {

		char[] ch = textView.getText().toString().toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (c == '+' || c == '-' || c == '×' || c == '÷' || c == '('
					|| c == ')') {
				list.add(c + "");
			} else {
				list.add(c + "");
				while (true) {
					if (i + 1 >= ch.length) {
						break;
					}
					char c1 = ch[i + 1];
					if (c1 == '+' || c1 == '-' || c1 == '×' || c1 == '÷'
							|| c1 == '(' || c1 == ')') {
						break;
					} else {
						list.add(list.get(list.size() - 1) + c1);
						list.remove(list.size() - 2);
						i++;
					}
				}

			}

		}

		// 如果第一位为负号
		if (list.get(0).equals("-")) {
			list.set(0, "-1");
			list.add(1, OPERATOR_MUL);
		}
		// 如果(后面是负号
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).equals("(") && list.size() > i + 1) {
				if (list.get(i + 1).equals("-")) {
					list.set(i + 1, "-1");
					list.add(i + 2, OPERATOR_MUL);
				}
			}
		}
		// 如果有比如 2(3-8)这样情况 或()()这样情况
		for (int i = 1; i < list.size(); i++) {
			if (list.get(i).equals("(")) {
				String s = list.get(i - 1);
				if (s.equals("+") || s.equals("-") || s.equals(OPERATOR_MUL)
						|| s.equals(OPERATOR_DIV) || s.equals("(")) {
					continue;
				} else {
					list.add(i, OPERATOR_MUL);
				}

			}
		}

		// 如果有比如 (3-6)9的情况 (3-9)9+1(2×2)+(1×4)(3-5)
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).equals(")") && list.size() > i + 1) {
				String s = list.get(i + 1);
				if (s.equals("+") || s.equals("-") || s.equals(OPERATOR_MUL)
						|| s.equals(OPERATOR_DIV) || s.equals(")")) {
					continue;
				} else {
					list.add(i + 1, OPERATOR_MUL);
				}
			}
		}

		// 如果有(81)这种情况存在
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).equals("(") && list.size() > i + 2
					&& list.get(i + 2).equals(")")) {
				list.add(i + 1, OPERATOR_MUL);
				list.add(i + 1, "1");
			}
		}
		for (String s : list) {
			System.out.println(s);
		}
	}

	public boolean isNum(String str) {
		return str.matches("^(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}

	int left, right, total;
	double result;
	boolean b;

	/**
	 * 计算
	 */
	public void operation() throws NumberFormatException {

		b = false;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).toString().equals("(")) {
				b = true;
				break;
			}
		}
		// 此循环去除()
		while (b) {
			// 先找到第一个 )
			System.out.println("12345");
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).equals(")")) {
					right = i;
					break;
				}
			}
			for (int i = right; i >= 0; i--) {
				if (list.get(i).equals("(")) {
					left = i;
					break;
				}
			}
			System.out.println(left + "  " + right);

			total = list.size();
			while (list.size() > total - right + left) {

				boolean ifhasoperator = false;
				for (int i = left; i < right; i++) {
					if (list.get(i).equals("+") || list.get(i).equals("-")
							|| list.get(i).equals(OPERATOR_MUL)
							|| list.get(i).equals(OPERATOR_DIV)) {
						ifhasoperator = true;

					}
				}
				if (!ifhasoperator) {
					break;
				}
				for (int i = left; i < right; i++) {
					if (list.get(i).toString().equals(OPERATOR_DIV)) {
						result = Double.parseDouble(list.get(i - 1).toString())
								/ Double.parseDouble(list.get(i + 1).toString());
						list.set(i, result + "");
						list.remove(i + 1);
						list.remove(i - 1);
						right = right - 2;
					}
				}

				for (int i = left; i < right; i++) {
					if (list.get(i).toString().equals(OPERATOR_MUL)) {
						result = Double.parseDouble(list.get(i - 1).toString())
								* Double.parseDouble(list.get(i + 1).toString());
						list.set(i, result + "");
						list.remove(i + 1);
						list.remove(i - 1);
						right = right - 2;
					}
				}
				for (int i = left; i < right; i++) {

					if (list.get(i).toString().equals("-")) {
						result = Double.parseDouble(list.get(i - 1).toString())
								- Double.parseDouble(list.get(i + 1).toString());
						list.set(i, result + "");
						list.remove(i + 1);
						list.remove(i - 1);
						right = right - 2;
					}
				}
				for (int i = left; i < right; i++) {
					if (list.get(i).toString().equals(OPERATOR_ADD)) {
						result = Double.parseDouble(list.get(i - 1).toString())
								+ Double.parseDouble(list.get(i + 1).toString());

						list.set(i, result + "");
						list.remove(i + 1);
						list.remove(i - 1);
						right = right - 2;
					}

				}
			}
			list.remove(right);
			list.remove(left);
			System.out.println("===");
			for (String s : list) {
				System.out.println(s);
			}
			b = false;
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).toString().equals("(")) {
					b = true;
					break;
				}
			}

		}

		b = false;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).toString().equals(OPERATOR_DIV)) {
				b = true;
				break;
			}
		}
		while (b) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).toString().equals(OPERATOR_DIV)) {
					result = Double.parseDouble(list.get(i - 1).toString())
							/ Double.parseDouble(list.get(i + 1).toString());
					list.set(i, result + "");
					list.remove(i + 1);
					list.remove(i - 1);
					break;
				}

			}
			b = false;
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).toString().equals(OPERATOR_DIV)) {
					b = true;
					break;
				}
			}
		}

		b = false;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).toString().equals(OPERATOR_MUL)) {
				b = true;
				break;
			}
		}
		while (b) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).toString().equals(OPERATOR_MUL)) {
					result = Double.parseDouble(list.get(i - 1).toString())
							* Double.parseDouble(list.get(i + 1).toString());
					list.set(i, result + "");
					list.remove(i + 1);
					list.remove(i - 1);
					break;
				}

			}
			b = false;
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).toString().equals(OPERATOR_MUL)) {
					b = true;
					break;
				}
			}
		}

		b = false;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).toString().equals("-")) {
				b = true;
				break;
			}
		}
		while (b) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).toString().equals("-")) {
					result = Double.parseDouble(list.get(i - 1).toString())
							- Double.parseDouble(list.get(i + 1).toString());
					list.set(i, result + "");
					list.remove(i + 1);
					list.remove(i - 1);
					break;
				}

			}
			b = false;
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).toString().equals("-")) {
					b = true;
					break;
				}
			}
		}
		b = false;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).toString().equals(OPERATOR_ADD)) {
				b = true;
				break;
			}
		}
		while (b) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).toString().equals(OPERATOR_ADD)) {
					result = Double.parseDouble(list.get(i - 1).toString())
							+ Double.parseDouble(list.get(i + 1).toString());
					list.set(i, result + "");
					list.remove(i + 1);
					list.remove(i - 1);
					break;
				}

			}
			b = false;
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).toString().equals("+")) {
					b = true;
					break;
				}
			}
		}

	}

	/**
	 * 判断算式有没有错误
	 * 
	 * @return
	 */
	boolean mistake = false;

	public void hasMistake() {
		String str = list.get(list.size() - 1);
		if (str.equals("+") || str.equals("-") || str.equals(OPERATOR_MUL)
				|| str.equals(OPERATOR_DIV) || str.equals("(")) {

			mistake = true;
		}
		int num1 = 0, num2 = 0;
		for (String s : list) {
			if (s.equals("(")) {
				num1++;
			}
			if (s.equals(")")) {
				num2++;
			}
		}
		if (num1 != num2) {
			mistake = true;
		}
	}

	public void dealResult() {
		if (list.get(0).indexOf("Infinity") != -1
				|| list.get(0).indexOf("出错啦") != -1)
			return;
		String s = list.get(0);
		// 当等于10.0的时候取出.0
		if (s.endsWith(".0")) {
			list.add(s.substring(0, s.length() - 2));
			list.remove(0);
		}
		// 当含有'.',超过12位,不含‘E’时
		if (s.contains(".") && s.length() > MAX_LENTH) {
			// 12222.676565656E-3
			// 12222.676565656E13
			if (s.contains("E")) {
				int indexE = s.indexOf("E");
				String endStr = s.substring(indexE, s.length());
				int startLen = MAX_LENTH - endStr.length();
				String startStr = s.substring(0, startLen);
				String resultStr = startStr + endStr;
				list.remove(0);
				list.add(resultStr);
			} else {
				list.add(s.substring(0, 13));
				list.remove(0);
			}
		}
	}

}
