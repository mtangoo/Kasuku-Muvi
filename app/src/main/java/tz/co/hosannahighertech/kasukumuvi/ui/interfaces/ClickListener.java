package tz.co.hosannahighertech.kasukumuvi.ui.interfaces;

import android.view.View;

/**
 * @package tz.co.hosannahighertech.kasukumuvi.ui.interfaces
 * Created by Stefano D. Mtangoo <stefano@hosannahighertech.co.tz> on
 * Created at 23/06/2018 14:24.
 * Copyright (c) 2018, Hosanna Higher Technologies Co. Ltd
 * This Code is Provided under Hosanna HTCL Licensing Conditions.
 */

public interface ClickListener {

    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
