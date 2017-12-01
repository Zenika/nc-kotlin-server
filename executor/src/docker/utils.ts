export function promisifyStream(stream: any, onData?: (n: any) => void): Promise<any> {
  return new Promise((resolve, reject) => {
    stream.on("end", resolve);
    stream.on("error", reject);
    if (onData) {
      stream.on("data", onData);
    }
  });
}
