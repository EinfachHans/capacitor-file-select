export interface FileSelectPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
