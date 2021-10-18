/*
 *  Copyright 2016 The WebRTC project authors. All Rights Reserved.
 *
 *  Use of this source code is governed by a BSD-style license
 *  that can be found in the LICENSE file in the root of the source
 *  tree. An additional intellectual property rights grant can be found
 *  in the file PATENTS.  All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */

package org.webrtc;
import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.util.Log;

import java.util.List;

@TargetApi(21)
public class Camera2Capturer extends CameraCapturer {
  private final Context context;
  private final CameraManager cameraManager;

  public Camera2Capturer(Context context, String cameraName, CameraEventsHandler eventsHandler) {
    super(cameraName, eventsHandler, new Camera2Enumerator(context));

    this.context = context;
    cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
  }

  @Override
  protected void createCameraSession(CameraSession.CreateSessionCallback createSessionCallback,
      CameraSession.Events events, Context applicationContext,
      SurfaceTextureHelper surfaceTextureHelper, String cameraName, int width, int height,
      int framerate) {
    Camera2Session.create(createSessionCallback, events, applicationContext, cameraManager,
        surfaceTextureHelper, cameraName, width, height, framerate);
  }



  /**
   +   * Returns true if zoom is supported. Applications should call this
   +   * before using other zoom methods.
   +   *
   +   * @return true if zoom is supported.
   +   */
  public boolean isZoomSupported() throws Camera2Capturer.CameraException {
    synchronized (stateLock) {
      try {
        return ((Camera2Session) currentSession).isZoomSupported();
      } catch (Exception e) {
        throw new Camera2Capturer.CameraException(e);
      }
    }
  }

  /**
   +   * Sets current zoom value.
   +   *
   +   * @param value zoom value. The valid range is 0 to {@link #getMaxZoom}.
   +   */
  public void setZoom(float value) throws CameraException {
    synchronized (stateLock) {
      try {
        ((Camera2Session) currentSession).setZoom(value);
      } catch (Exception e) {
        throw new Camera2Capturer.CameraException(e);
      }
    }
  }

  /**
   +   * Gets the maximum zoom value allowed for snapshot.
   +   * Applications should call {@link #isZoomSupported} before using this
   +   * method. This value may change in different preview size. Applications
   +   * should call this again after setting preview size.
   +   *
   +   * @return the maximum zoom value supported by the camera.
   +   */
  public float getZoom() throws Camera2Capturer.CameraException {
    synchronized (stateLock) {
      try {
        return ((Camera2Session) currentSession).getZoom();
      } catch (Exception e) {
        throw new Camera2Capturer.CameraException(e);
      }
    }
  }


  /**
   +   * Gets the maximum zoom value allowed for snapshot.
   +   * Applications should call {@link #isZoomSupported} before using this
   +   * method. This value may change in different preview size. Applications
   +   * should call this again after setting preview size.
   +   *
   +   * @return the maximum zoom value supported by the camera.
   +   */
  public float getMaxZoom() throws Camera2Capturer.CameraException {
    synchronized (stateLock) {
      try {
        return ((Camera2Session) currentSession).getMaxZoom();
      } catch (Exception e) {
        throw new Camera2Capturer.CameraException(e);
      }
    }
  }

  /**
   +   * Gets the zoom ratios of all zoom values. Applications should check
   +   * {@link #isZoomSupported} before using this method.
   +   *
   +   * @return the zoom ratios in 1/100 increments. Ex: a zoom of 3.2x is
   +   * returned as 320. The number of elements is {@link
  +   * #getMaxZoom} + 1. The list is sorted from small to large. The
   +   * first element is always 100. The last element is the zoom
   +   * ratio of the maximum zoom value.
   +   */
  public List<Integer> getZoomRatios() throws Camera2Capturer.CameraException {
    synchronized (stateLock) {
      try {
        return ((Camera2Session) currentSession).getZoomRatios();
      } catch (Exception e) {
        throw new Camera2Capturer.CameraException(e);
      }
    }
  }

  public class CameraException extends Exception{
    CameraException(Exception e){
      super(e.getMessage(),e.getCause());
    }
  }
}
