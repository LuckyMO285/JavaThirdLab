package ru.spbstu.telematics.java;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.*;
import java.util.*;
import java.util.regex.*;
import java.lang.*;;

class MyThread extends Thread {
	public static final Pattern pattern = Pattern.compile
			("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

	public int begin;
	public int end;

	public int result;

	MyThread(int a, int b){
		begin = a;
		end = b;
	}

	@Override
	public void run() {
		long start = System.currentTimeMillis();
		for (int i = begin; i <= end; i++){
			try {
				BufferedReader in = new BufferedReader(new FileReader("/home/luck/JavaThirdLab/"+i));
				String str = in.readLine();
				int j = 0;

				while(str != null){
					String word = "";
					while (str.equals(""))
						str = in.readLine();

					while ((j <= str.length()) && str.charAt(j) != '>' && str.charAt(j) != '<' && str.charAt(j) != ' ') {
						word+=str.charAt(j);
						j++;
						if (j == str.length())
							break;
					}

					if (word.length() == 0)
						j++;
					Matcher matcher = pattern.matcher(word);
					if (matcher.find()){
						if (end == 2)
							System.out.println("1 - "+word);
						else if (end == 4)
							System.out.println("2 - "+word);
						else if (end == 6)
							System.out.println("3 - "+word);
						else
							System.out.println("4 - "+word);
						/*if (end == 3)
							System.out.println("1 - "+word);
						else
							System.out.println("2 - "+word);*/
					}
					if (j == str.length()){
						str = in.readLine();
						j = 0;
					}
				}
				in.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		long finish = System.currentTimeMillis(); 
		long timeConsumedMillis = finish - start; 
		/*if (end == 3)
			System.out.println("1 - "+timeConsumedMillis);
		else
			System.out.println("2 - "+timeConsumedMillis);*/

		if (end == 2)
			System.out.println("1 - "+timeConsumedMillis);
		else if (end == 4)
			System.out.println("2 - "+timeConsumedMillis);
		else if (end == 6)
			System.out.println("3 - "+timeConsumedMillis);
		else if (end == 8)
			System.out.println("4 - "+timeConsumedMillis);

	}
}

public class App {

	public static void main(String[] args) throws Exception {
		ArrayList<String> list = new ArrayList<String>();
		list.add("https://lenta.ru/");
		list.add("https://otvet.mail.ru/question/8449918");
		list.add("http://ktonanovenkogo.ru/web-obzory/elektronnaya-pochta-sozdat-zaregistrirovat-pochtovyj-yashhik-vybor-luchshego-besplatnyx-email.html");
		//list.add("http://www.liveinternet.ru/users/pomogai-ka/post209216462/");
		list.add("http://www.3dnews.ru/572806");
		//list.add("https://vk.com/topic-59372349_29077381");
		list.add("http://studiowebd.ru/forum/topic/144/");
		list.add("https://habrahabr.ru/post/140954/");
		list.add("https://en.wikipedia.org/wiki/Email");
		//list.add("https://www.vardex.ru/shops/sankt-petersburg/");

		for (int i = 1; i <= list.size(); i++){
			URL url = new URL(list.get(i-1));
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(url.openConnection().getInputStream(), "UTF-8"));
			FileWriter writer = new FileWriter("/home/luck/JavaThirdLab/"+i);
			while (true) {
				String line = reader.readLine();
				if (line == null)
					break;
				writer.write(line);
				writer.write('\n');
				writer.flush();
			}
			writer.close();
		}

		/*MyThread t1 = new MyThread(1,3);
		t1.start();
		MyThread t2 = new MyThread(4,7);
		t2.start();

		t1.join();
		t2.join();*/

		MyThread t1 = new MyThread(1,2);
		t1.start();
		MyThread t2 = new MyThread(3,4);
		t2.start();
		MyThread t3 = new MyThread(5,6);
		t3.start();
		MyThread t4 = new MyThread(7,8);
		t4.start();

		t1.join();
		t2.join();
		t3.join();
		t4.join();
	}
}
