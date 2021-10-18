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
import java.util.List;
import android.content.Context;

public class Camera1Capturer extends CameraCapturer {
  private final boolean captureToTexture;

  public Camera1Capturer(
      String cameraName, CameraEventsHandler eventsHandler, boolean captureToTexture) {
    super(cameraName, eventsHandler, new Camera1Enumerator(captureToTexture));

    this.captureToTexture = captureToTexture;
  }

  @Override
  protected void createCameraSession(CameraSession.CreateSessionCallback createSessionCallback,
      CameraSession.Events events, Context applicationContext,
      SurfaceTextureHelper surfaceTextureHelper, String cameraName, int width, int height,
      int framerate) {
    Camera1Session.create(createSessionCallback, events, captureToTexture, applicationContext,
        surfaceTextureHelper, Camera1Enumerator.getCameraIndex(cameraName), width, height,
        framerate);
  }

  /**
   +   * Returns true if zoom is supported. Applications should call this
   +   * before using other zoom methods.
   +   *
   +   * @return true if zoom is supported.
   +   */
  public boolean isZoomSupported() throws CameraException {
    synchronized (stateLock) {
      try {
        return ((Camera1Session) currentSession).isZoomSupported();
      } catch (Exception e) {
        throw new CameraException(e);
      }
    }
  }

  /**
   +   * Sets current zoom value.
   +   *
   +   * @param value zoom value. The valid range is 0 to {@link #getMaxZoom}.
   +   */
  public void setZoom(int value) throws CameraException {
    synchronized (stateLock) {
      try {
        ((Camera1Session) currentSession).setZoom(value);
      } catch (Exception e) {
        throw new CameraException(e);
      }
    }
  }

  /**
   +   * Gets the maximum zoom value allowed for snapshot. This is the maximum
   +   * value that applications can set to {@link #setZoom(int)}.
   +   * Applications should call {@link #isZoomSupported} before using this
   +   * method. This value may change in different preview size. Applications
   +   * should call this again after setting preview size.
   +   *
   +   * @return the maximum zoom value supported by the camera.
   +   */
  public int getZoom() throws CameraException {
    synchronized (stateLock) {
      try {
        return ((Camera1Session) currentSession).getZoom();
      } catch (Exception e) {
        throw new CameraException(e);
      }
    }
  }


  /**
   +   * Gets the maximum zoom value allowed for snapshot. This is the maximum
   +   * value that applications can set to {@link #setZoom(int)}.
   +   * Applications should call {@link #isZoomSupported} before using this
   +   * method. This value may change in different preview size. Applications
   +   * should call this again after setting preview size.
   +   *
   +   * @return the maximum zoom value supported by the camera.
   +   */
  public int getMaxZoom() throws CameraException {
    synchronized (stateLock) {
      try {
        return ((Camera1Session) currentSession).getMaxZoom();
      } catch (Exception e) {
        throw new CameraException(e);
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
  public List<Integer> getZoomRatios() throws CameraException {
    synchronized (stateLock) {
      try {
        return ((Camera1Session) currentSession).getZoomRatios();
      } catch (Exception e) {
        throw new CameraException(e);
      }
    }
  }

  @Override
  public void setZoom(float zoomValue) {

  }


  public class CameraException extends Exception{

    CameraException(Exception e){
      super(e.getMessage(),e.getCause());
    }
  }
}
