export interface FileSelectPlugin {
  /**
   * Opens the File Selector
   */
  select(options: FileSelectOptions): Promise<{files: FileSelectResult[]}>;
}

export interface FileSelectOptions {
  /**
   * Select multiple Files
   */
  multiple: boolean;
  /**
   * Extensions to select
   */
  extensions: string[];
}

export interface FileSelectResult {
  /**
   * File Path
   */
  path: boolean;
  /**
   * File Name
   */
  name: string;
  /**
   * File Extensions
   */
  extension: string;
}
