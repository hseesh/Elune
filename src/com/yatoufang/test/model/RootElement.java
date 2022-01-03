package com.yatoufang.test.model;

import com.intellij.ui.JBColor;
import com.yatoufang.config.MindMapConfig;
import com.yatoufang.test.component.Crayons;

import java.awt.*;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

/**
 * @author GongHuang（hse）
 * @since 2021/12/25 0025
 */
public class RootElement extends AbstractElement {
    public RootElement(String text) {
        super(text);
    }

    private final Dimension2D leftBlockSize = new Dimension();
    private final Dimension2D rightBlockSize = new Dimension();

    private Shape makeShape(final double x, final double y) {
        final float round = 1.0f;
        return new RoundRectangle2D.Double(x, y, this.bounds.getWidth(), this.bounds.getHeight(), round, round);
    }


    @Override
    public boolean movable() {
        return false;
    }

    @Override
    public boolean isCollapsed() {
        return false;
    }

    @Override
    public Color getBackgroundColor() {
        return MindMapConfig.rootBackgroundColor;
    }

    @Override
    public Color getTextColor() {
        return MindMapConfig.rootTextColor;
    }

    @Override
    public boolean hasDirection() {
        return false;
    }

    @Override
    public Dimension2D calcBlockSize(Dimension2D size, final boolean childrenOnly) {
        final double insetV = MindMapConfig.scale * MindMapConfig.firstLevelVerticalInset;
        final double insetH = MindMapConfig.scale * MindMapConfig.firstLevelHorizontalInset;

        double leftWidth = 0.0d;
        double leftHeight = 0.0d;
        double rightWidth = 0.0d;
        double rightHeight = 0.0d;

        rightWidth = Math.max(rightWidth, size.getWidth());
        rightHeight += size.getHeight();

        if (!childrenOnly) {
            rightWidth += insetH;
        }

        this.leftBlockSize.setSize(leftWidth, leftHeight);
        this.rightBlockSize.setSize(rightWidth, rightHeight);

        if (childrenOnly) {
            size.setSize(leftWidth + rightWidth, Math.max(leftHeight, rightHeight));
        } else {
            size.setSize(leftWidth + rightWidth + this.bounds.getWidth(), Math.max(this.bounds.getHeight(), Math.max(leftHeight, rightHeight)));
        }

        return size;
    }

    @Override
    public void add(RootElement element) {
        this.children.add(element);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(JBColor.BLUE);
        g.drawRect((int) bounds.getX() - 5,(int) bounds.getY() - 5,(int)bounds.getWidth() + 10,(int) bounds.getWidth() + 10);
        for (AbstractElement child : children) {
            g.setColor(JBColor.RED);
            int x = (int) child.bounds.getX() - 5;
            int y = (int) child.bounds.getY() - 5;
            int width = (int) child.bounds.getWidth() + 10;
            int height = (int) child.bounds.getHeight() + 10;
            System.out.println("x = " + x + " y = " + y + " width = " + width + " height = " + height);

            g.drawLine(x, y, x + width - 1, y);
            g.drawLine(x + width, y, x + width, y + height - 1);
            g.drawLine(x + width, y + height, x + 1, y + height);
            g.drawLine(x, y + height, x, y + 1);
        }
    }

    @Override
    public void setBounds(int x, int y, int x1, int y1) {
        this.bounds.setFrame(x, y, x1, y1);
    }

    @Override
    public void drawComponent() {
        Crayons.setStroke(1f, StrokeType.SOLID);

        final Shape shape = makeShape(1f, 1f);

        if (MindMapConfig.dropShadow) {
            Crayons.draw(makeShape(MindMapConfig.shadowOffset, MindMapConfig.shadowOffset), null, MindMapConfig.shadowColor);
        }

        Crayons.draw(shape, MindMapConfig.elementBorderColor, MindMapConfig.rootBackgroundColor);

        textArea.paint(MindMapConfig.rootTextColor);
    }

    @Override
    public void drwLinkLine(Rectangle2D source, Rectangle2D destination) {
        Crayons.setStroke(1f, StrokeType.SOLID);

        final double startX;
        if (destination.getCenterX() < source.getCenterX()) {
            // left
            startX = source.getCenterX() - source.getWidth() / 4;
        } else {
            // right
            startX = source.getCenterX() + source.getWidth() / 4;
        }

        Crayons.drawCurve(startX, source.getCenterY(), destination.getCenterX(), destination.getCenterY(), MindMapConfig.linkLineColor);

    }

    public Dimension2D getLeftBlockSize() {
        return this.leftBlockSize;
    }


    public Dimension2D getRightBlockSize() {
        return this.rightBlockSize;
    }


}
