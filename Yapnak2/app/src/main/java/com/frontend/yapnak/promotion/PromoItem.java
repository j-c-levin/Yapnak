package com.frontend.yapnak.promotion;

import android.widget.TextView;

/**
 * Created by vahizan on 25/06/2015.
 */
public class PromoItem {

    private String promoTitle;
    private String promoSubTitle;
    private int image;

    public String getPromoTitle() {
        return promoTitle;
    }

    public void setPromoTitle(String promoTitle) {
        this.promoTitle = promoTitle;
    }

    public String getPromoSubTitle() {
        return promoSubTitle;
    }

    public void setPromoSubTitle(String promoSubTitle) {
        this.promoSubTitle = promoSubTitle;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
