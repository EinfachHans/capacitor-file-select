import { registerPlugin } from '@capacitor/core';

import type { FileSelectPlugin } from './definitions';

const FileSelect = registerPlugin<FileSelectPlugin>('FileSelect', {
  web: () => import('./web').then(m => new m.FileSelectWeb()),
});

export * from './definitions';
export { FileSelect };
