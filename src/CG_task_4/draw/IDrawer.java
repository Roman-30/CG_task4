/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CG_task_4.draw;

import CG_task_4.third.PolyLine3D;

import java.util.Collection;

/**
 * Интерфейс, описывающий в общем виде процесс рисования полилинии
 * @author Alexey
 */
public interface IDrawer {
    /**
     * Очищает область заданным цветом
     * @param color цвет
     */
    void clear(int color);
    
    /**
     * Рисует все полилинии
     * @param polyline набор рисуемых полилиний.
     */
    void draw(Collection<PolyLine3D> polyline);
}
