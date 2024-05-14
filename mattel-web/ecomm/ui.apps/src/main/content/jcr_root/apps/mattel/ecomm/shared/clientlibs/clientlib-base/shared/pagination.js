/*
Example:
createPagination.init(document.getElementById('pagination'), {
    size: pageSize, // pages size
    page: pageNo, // selected page
    step: 3 // pages before and after current
});
*/

import Constants from './constant';

let self;
export default class pagination {
  constructor() {
    self = this;
  }
  create(elem) {
    const html = `<a role="button" class="btn page-link page-first" data-page='1'></a><span></span><a role="button" class="page-link page-next btn" data-page="${self.size}"></a>`;
    elem.innerHTML = html;
    self.elem = elem.getElementsByTagName("span")[0];
  }
  start() {
    if (self.size < self.step * 2 + 6) {
      self.add(1, self.size + 1);
    } else if (self.page < self.step * 2 + 1) {
      self.add(1, self.step * 2 + (Constants.isMobile ? 0 : 4)); // pagination cnt in a arrow
      self.last();
    } else if (self.page > self.size - self.step * 2) {
      self.first();
      self.add(self.size - self.step * 2 - 2, self.size + 1);
    } else {
      self.first();
      self.add(self.page - self.step, self.page + self.step + 1);
      self.last();
    }
    self.finish();
  }
  add(start, count) {
    for (var i = start; i < count; i++) {
      self.code += `<a class="page-link" data-page=${i}>${i}</a>`;
    }
  }
  last() {
    self.code +=`<i>...</i><a class="page-link" data-page='${self.size}'>${self.size}</a>`;
  }
  // add first page with separator
  first() {
    self.code += '<a class="page-link" data-page="1">1</a><i>...</i>';
  }
  bind() {
    const a = self.elem.getElementsByTagName("a");
    for (var i = 0; i < a.length; i++) {
      if (+a[i].innerHTML === self.page) a[i].classList.add("current");
    }
  }
  // write pagination
  finish() {
    self.elem.innerHTML = self.code;
    self.code = "";
    self.bind();
  }
  init(elem,config){
    const data = config || {};
    self.size = data.size || 30;
    self.page = data.page || 1;
    self.step = data.step || 3;
    self.code='';
    self.create(elem);
    self.start();
  }
}
