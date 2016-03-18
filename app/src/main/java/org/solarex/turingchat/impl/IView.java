package org.solarex.turingchat.impl;

import org.solarex.turingchat.bean.Msg;

import java.util.ArrayList;


public interface IView {
    void updateUI(ArrayList<Msg> msgs);
    void notifyInputError();
}
