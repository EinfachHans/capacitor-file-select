import { WebPlugin } from '@capacitor/core';

import type { FileSelectOptions, FileSelectPlugin } from './definitions';

export class FileSelectWeb extends WebPlugin implements FileSelectPlugin {
  async select(options: FileSelectOptions): Promise<any> {
    const x: HTMLInputElement = document.createElement("INPUT") as HTMLInputElement;
    x.hidden = true;
    x.setAttribute("type", "file");
    x.setAttribute("id", "filePicker");

    let accept = "";
    options.extensions.forEach(element => {
      if (element == 'images') {
        accept = accept.concat("image/*,");
      } else if (element == 'videos') {
        accept = accept.concat("video/*,");
      } else if (element == 'audios') {
        accept = accept.concat("audio/*,");
      }
    });

    let index = options.extensions.indexOf("images");
    if (index > -1) {
      options.extensions.splice(index, 1);
    }

    index = options.extensions.indexOf("videos");
    if (index > -1) {
      options.extensions.splice(index, 1);
    }

    index = options.extensions.indexOf("audios");
    if (index > -1) {
      options.extensions.splice(index, 1);
    }

    options.extensions = options.extensions.map(v => "." + v);
    accept = accept.concat(options.extensions.join(","));
    x.setAttribute("accept", accept);

    if (options.multiple) {
      x.setAttribute("multiple", "true");
    }

    return new Promise(resolve => {
      x.addEventListener("change",
        () => {
          if (document != null) {
            resolve(x.files);
          }
        });
      x.click();
    });

  }
}
