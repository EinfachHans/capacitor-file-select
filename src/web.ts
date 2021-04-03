import { WebPlugin } from '@capacitor/core';

import type { FileSelectPlugin } from './definitions';

export class FileSelectWeb extends WebPlugin implements FileSelectPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
