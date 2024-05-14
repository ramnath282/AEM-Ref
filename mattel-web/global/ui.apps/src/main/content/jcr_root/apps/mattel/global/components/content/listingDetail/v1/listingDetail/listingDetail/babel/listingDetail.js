class listingComponentInfo {
  constructor () {
    self = this;
    window.global.eventBindingInst.bindLooping(self.bindingEventsConfig(), self);
  }

  init () {
    window.global.eventBindingInst.bindLooping({
    }, self)
  }

  bindingEventsConfig() {
  }
}

window.global.listingComponentInstance = window.global.listingComponentInstance || new listingComponentInfo();
window.global.listingComponentInstance.init();
