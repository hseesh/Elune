package com.yatoufang.test.controller;

import com.yatoufang.test.event.EventService;
import com.yatoufang.test.event.EventType;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author GongHuang（hse）
 * @since 2022/3/31 0031
 */
public class MyMouseAdapter extends MouseAdapter {
    /**
     * {@inheritDoc}
     *
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        EventService.post(e, EventType.MOUSE_CLICK);
    }
}
