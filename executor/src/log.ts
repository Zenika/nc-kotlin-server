export function makeLog(icon1: string) {
  return (message: any, ...optionalParams: any[]) => {
    console.log(`${new Date().toISOString()}\t${icon1}\t${message}`, ...optionalParams);
  };
}
