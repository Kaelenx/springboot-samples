function apiBase(): string {
  const fromEnv = import.meta.env.VITE_API_BASE?.trim();
  if (fromEnv) return fromEnv.replace(/\/$/, "");
  /* 浏览器 H5：走 Vite 代理 /api；小程序等无 window.location：直连后端（真机请配置 VITE_API_BASE） */
  return typeof location !== "undefined" ? "/api" : "http://10.43.1.130:8080/api";
}

function joinUrl(
  path: string,
  params?: Record<string, string | number | boolean>,
) {
  const base = apiBase();
  const p = path.startsWith("/") ? path : `/${path}`;
  const url = path.startsWith("http") ? path : `${base}${p}`;
  if (!params || !Object.keys(params).length) return url;
  const q = Object.entries(params)
    .filter(([, v]) => v !== undefined && v !== null && v !== "")
    .map(
      ([k, v]) => `${encodeURIComponent(k)}=${encodeURIComponent(String(v))}`,
    )
    .join("&");
  return q ? `${url}?${q}` : url;
}

export function httpGet<T = unknown>(
  path: string,
  opts?: { params?: Record<string, string | number | boolean> },
): Promise<T> {
  const url = joinUrl(path, opts?.params);
  return new Promise((resolve, reject) => {
    uni.request({
      url,
      method: "GET",
      timeout: 30_000,
      success: (res) => {
        if (res.statusCode >= 200 && res.statusCode < 300) {
          resolve(res.data as T);
        } else {
          reject(new Error(`HTTP ${res.statusCode}`));
        }
      },
      fail: (e) => reject(e),
    });
  });
}
