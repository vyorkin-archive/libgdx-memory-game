using System;
using MonoTouch.Foundation;
using MonoTouch.UIKit;

using playn.ios;
using playn.core;
using com.vyorkin.game.core;

namespace com.vyorkin.game
{
  [Register ("AppDelegate")]
  public partial class AppDelegate : IOSApplicationDelegate {
    public override bool FinishedLaunching (UIApplication app, NSDictionary options) {
      app.SetStatusBarHidden(true, true);
      var pf = IOSPlatform.register(app, IOSPlatform.SupportedOrients.PORTRAITS);
      pf.assets().setPathPrefix("assets");
      PlayN.run(new MemoryGame());
      return true;
    }
  }

  public class Application {
    static void Main (string[] args) {
      UIApplication.Main (args, null, "AppDelegate");
    }
  }
}
