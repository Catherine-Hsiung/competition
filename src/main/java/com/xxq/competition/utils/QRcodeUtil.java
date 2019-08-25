package com.xxq.competition.utils;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.*;

public class QRcodeUtil {
    public static final int BLACK = 0xFF000000;
    public static final int WRITE = 0xFF000000;
    public static final List<String> IMG_TYPE = new ArrayList<>();

    static {
        IMG_TYPE.add(".jpg");
        IMG_TYPE.add(".png");
    }

    /**
     * 生成二维码
     *
     * @param content
     * @param path
     * @param size
     * @param logoPath
     * @return
     */
    public static boolean zxingCodeCreate(String content, String path, Integer size, String logoPath) {
        try {
            final String imgType = ".jpg";
            BufferedImage bi = getBufferedImage(content, size, logoPath);
            Random random = new Random();
            File file = new File(path + random.nextInt(1000) + imgType);
            if (!file.exists()) {
                file.mkdir();
            }
            ImageIO.write(bi, imgType, file);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * @param content
     * @param size
     * @param logoPath
     * @return
     */
    public static BufferedImage getBufferedImage(String content, Integer size, String logoPath) {
        if (size == null || size <= 0) {
            size = 250;
        }
        BufferedImage image = null;
        try {
            //设置字符编码集
            Map<EncodeHintType, Object> hint = new HashMap<>();
            //设置编码
            hint.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            //设置容错率
            hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            hint.put(EncodeHintType.MARGIN, 1);
            //生成二维码
            MultiFormatWriter mfw = new MultiFormatWriter();
            BitMatrix bm = mfw.encode(content, BarcodeFormat.QR_CODE, size, size, hint);
            //获取二维码尺寸
            int codeWidth = bm.getWidth();
            int codeHeight = bm.getHeight();
            //将二维码放入流中
            image = new BufferedImage(codeWidth, codeWidth, BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < codeWidth; i++) {
                for (int j = 0; j < codeHeight; j++) {
                    image.setRGB(i, j, bm.get(i, j) ? BLACK : WRITE);
                }
            }
            //判断是否写入logo图片
            if (logoPath != null && !"".equals(logoPath)) {
                File logoPic = new File(logoPath);
                if (logoPic.exists()) {
                    Graphics2D g = image.createGraphics();
                    BufferedImage logo = ImageIO.read(logoPic);
                    int widthLogo = logo.getWidth(null) > image.getWidth() * 2 / 10 ? (image.getWidth() * 2 / 10) : logo.getWidth(null);
                    int heightLogo = logo.getHeight(null) > image.getHeight() * 2 / 10 ? (image.getHeight() * 2 / 10) : logo.getHeight(null);
                    int x = (image.getWidth() - widthLogo) / 2;
                    int y = (image.getHeight() - heightLogo) / 2;
                    //绘制图片
                    g.drawImage(logo, x, y, widthLogo, heightLogo, null);
                    g.drawRoundRect(x, y, widthLogo, heightLogo, 15, 15);
                    //设置边框宽度
                    g.setStroke(new BasicStroke(2));
                    //设置边框颜色
                    g.setColor(Color.WHITE);
                    g.drawRect(x, y, widthLogo, heightLogo);
                    g.dispose();
                    logo.flush();
                    image.flush();
                }
            }

        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public static boolean zxingCodeCreate(File qrPic, File logoPic, String path) {
        try {
            String imageType = path.substring(path.lastIndexOf(".") + 1).toLowerCase();
            if (!IMG_TYPE.contains(imageType)) {
                return false;
            }
            if (!qrPic.isFile() && !logoPic.isFile()) {
                return false;
            }
            //读取二维码图片，并构建绘图对象
            BufferedImage image = ImageIO.read(qrPic);
            Graphics2D g = image.createGraphics();
            //读取Logo图片
            BufferedImage logo = ImageIO.read(logoPic);
            //设置logo的大小,最多20%0
            int widthLogo = logo.getWidth(null) > image.getWidth() * 2 / 10 ? (image.getWidth() * 2 / 10) : logo.getWidth(null);
            int heightLogo = logo.getHeight(null) > image.getHeight() * 2 / 10 ? (image.getHeight() * 2 / 10) : logo.getHeight(null);
            // 计算图片放置位置，默认在中间
            int x = (image.getWidth() - widthLogo) / 2;
            int y = (image.getHeight() - heightLogo) / 2;
            // 开始绘制图片
            g.drawImage(logo, x, y, widthLogo, heightLogo, null);
            g.drawRoundRect(x, y, widthLogo, heightLogo, 15, 15);
            //边框宽度
            g.setStroke(new BasicStroke(2));
            //边框颜色
            g.setColor(Color.WHITE);
            g.drawRect(x, y, widthLogo, heightLogo);
            g.dispose();
            logo.flush();
            image.flush();
            File newFile = new File(path);
            if (!newFile.exists()) {
                newFile.mkdirs();
            }
            ImageIO.write(image, imageType, newFile);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 解析二维码
     *
     * @param path
     * @return
     */
    public static Result zxingCodeAnalyze(String path) {
        try {
            MultiFormatReader multiFormatReader = new MultiFormatReader();
            File file = new File(path);
            if (file.exists()) {
                BufferedImage image = ImageIO.read(file);
                LuminanceSource source = new BufferedImageLuminanceSource(image);
                Binarizer binarizer = new HybridBinarizer(source);
                BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
                Map hint = new HashMap();
                hint.put(EncodeHintType.CHARACTER_SET, "UTF-8");
                Result result = multiFormatReader.decode(binaryBitmap, hint);
                return result;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
