<%@page import="java.awt.geom.AffineTransform"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="image/jpeg" import="java.awt.*,
java.awt.image.*,java.util.*,javax.imageio.*,java.io.*" %>

<%!
/*
字体相关配置
*/
//可选字符列表
char[] chars = "2345678ABCDEFGHJKLMPQRSTUVWXYabcdefhkmnqrstuvwx"
.toCharArray();
//可选字体
String[] fontNames = new String[] { "Courier", "Arial",
        "Verdana", "Georgia", "Times", "Tahoma" };
//可选字体
int[] fontStyle = new int[] { Font.PLAIN, Font.BOLD, Font.ITALIC, Font.BOLD | Font.ITALIC };
//宽、高、生成字符个数、干扰线数量
int width = 100;
int height = 30;
int charCnt = 4;
int disturbLineNum = 10;

//随机角度
private double getRandomArch() {
    return ((int) (Math.random() * 1000) % 2 == 0 ? -1 : 1) * Math.random();
}
//随机颜色
private Color getRandomColor() {
    int r = (int) (Math.random() * 10000) % 200;
    int g = (int) (Math.random() * 10000) % 200;
    int b = (int) (Math.random() * 10000) % 200;
    return new Color(r, g, b);
}
//随机字体
private String getRandomFontName() {
    int pos = (int) (Math.random() * 10000) % (fontNames.length);
    return fontNames[pos];
}
//随机字形
private int getRandomStyle() {
    int pos = (int) (Math.random() * 10000) % (fontStyle.length);
    return fontStyle[pos];
}
//随机字体大小
private int getRandomSize() {
    int max = (int) (this.height * 0.9);
    int min = (int) (this.height * 0.6);
    return (int) (Math.random() * 10000) % (max - min + 1) + min;
}
//随机字符
private char[] generateCode() {
    char[] ret = new char[charCnt];
    for (int i = 0; i < charCnt; i++) {
        int letterPos = (int) (Math.random() * 10000) % (chars.length);
        ret[i] = chars[letterPos];
    }
    return ret;
}
//生成字符图片
private BufferedImage generateBuffImg(char c) {
    String tmp = Character.toString(c);
    Color forecolor = getRandomColor();
    Color backcolor = new Color(255, 255, 255, 0);
    String fontName = getRandomFontName();
    int fontStyle = getRandomStyle();
    int fontSize = getRandomSize();
    int strX = (this.height - fontSize) / 2;
    int strY = (this.height - fontSize) / 2 + fontSize;
    double arch = getRandomArch();

    BufferedImage ret = new BufferedImage(this.height, this.height,
            BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = ret.createGraphics();
    g.setColor(backcolor);
    g.fillRect(0, 0, this.height, this.height);

    g.setColor(forecolor);
    g.setFont(new Font(fontName, fontStyle, fontSize));
    g.rotate(arch, this.height / 2, this.height / 2);
    g.drawString(tmp, strX, strY);

    g.dispose();
    return ret;
}
%>

<%
response.reset();
//设置页面不缓存
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires", 0);


/*
* 绘制验证码图片
*/
BufferedImage bi = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
Graphics2D g = bi.createGraphics();
g.setColor(new Color(245, 245, 245));
g.fillRect(0, 0, width, height);

/*
* 绘制干扰线
*/
for (int i = 0; i < disturbLineNum; i++) {
    g.setColor(getRandomColor());
    int x = (int) (Math.random() * 10000) % (width + 1) + 1;
    int x1 = (int) (Math.random() * 10000) % (width + 1) + 1;
    int y = (int) (Math.random() * 10000) % (height + 1) + 1;
    int y1 = (int) (Math.random() * 10000) % (height + 1) + 1;
    g.drawLine(x, y, x1, y1);
}


//绘制生成的字符
BufferedImage[] bis = new BufferedImage[charCnt];
char[] codes = generateCode();
for (int i = 0; i < charCnt; i++) {
    bis[i] = generateBuffImg(codes[i]);
    g.drawImage(bis[i], null, (int) (this.height * 0.7) * i, 0);
}


//将认证码存入SESSION
session.setAttribute("rand",new String(codes));

//图象生效
g.dispose();

//输出图象到页面
ImageIO.write(bi, "gif", response.getOutputStream());
//清空流，返回新out对象
out.clear();
out = pageContext.pushBody();
%>


