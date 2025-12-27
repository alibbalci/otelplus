const API_BASE = import.meta.env.VITE_API_BASE_URL || "";

function buildHeaders() {
  const headers = { "Content-Type": "application/json" };
  const token = localStorage.getItem("otelplus_token");
  if (token) {
    headers.Authorization = `Bearer ${token}`;
  }
  return headers;
}

async function handleResponse(response) {
  if (response.status === 204) {
    return null;
  }
  const contentType = response.headers.get("content-type") || "";
  const data = contentType.includes("application/json")
    ? await response.json()
    : await response.text();
  if (!response.ok) {
    const message = typeof data === "string" ? data : JSON.stringify(data);
    throw new Error(message || "Request failed");
  }
  return data;
}

export async function get(path) {
  const response = await fetch(`${API_BASE}${path}`, {
    headers: buildHeaders(),
  });
  return handleResponse(response);
}

export async function post(path, body) {
  const response = await fetch(`${API_BASE}${path}`, {
    method: "POST",
    headers: buildHeaders(),
    body: JSON.stringify(body),
  });
  return handleResponse(response);
}

export async function put(path, body) {
  const response = await fetch(`${API_BASE}${path}`, {
    method: "PUT",
    headers: buildHeaders(),
    body: JSON.stringify(body),
  });
  return handleResponse(response);
}

export async function del(path) {
  const response = await fetch(`${API_BASE}${path}`, {
    method: "DELETE",
    headers: buildHeaders(),
  });
  return handleResponse(response);
}
