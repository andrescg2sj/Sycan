package org.sj.utils.math.symbol.mathrender;

import java.awt.Rectangle;
import java.awt.Graphics;

public interface Renderable {

    public abstract Rectangle getBounds();

    /**
     * @param x coordenada x de la posición donde se debe dibujar
     * @param y coordenada y de la posición donde se debe dibujar
     */
    public abstract void render(Graphics g, int x, int y);


   
}
