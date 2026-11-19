const usuarioForm = document.querySelector("#usuario-form");
const pautaForm = document.querySelector("#pauta-form");
const usuariosTable = document.querySelector("#usuarios");
const pautasContainer = document.querySelector("#pautas");
const mensagem = document.querySelector("#mensagem");
const pautaMensagem = document.querySelector("#pauta-mensagem");
const usuarioMensagem = document.querySelector("#usuario-mensagem");

function mostrarMensagem(texto, tipo = "success") {
    mensagem.textContent = texto;
    mensagem.className = `message ${tipo}`;
}

function mostrarMensagemPauta(texto, tipo = "success") {
    pautaMensagem.textContent = texto;
    pautaMensagem.className = `message ${tipo}`;
}

function mostrarMensagemUsuario(texto, tipo = "success") {
    usuarioMensagem.textContent = texto;
    usuarioMensagem.className = `message ${tipo}`;
}

async function api(url, options = {}) {
    const response = await fetch(url, {
        ...options,
        headers: {
            Accept: "application/json",
            ...(options.body ? { "Content-Type": "application/json; charset=UTF-8" } : {}),
            ...options.headers
        }
    });

    const body = await response.text();
    let data = null;
    if (body) {
        try {
            data = JSON.parse(body);
        } catch (_) {
            data = null;
        }
    }

    if (!response.ok) {
        const detalhe = data?.detail || data?.message || data?.error || data?.title || body;
        throw new Error(detalhe || `Falha na API (HTTP ${response.status}).`);
    }

    if (response.status === 204) return null;
    return data;
}

async function carregarUsuarios() {
    try {
        const usuarios = await api("/usuarios");
        usuariosTable.innerHTML = usuarios.length ? usuarios.map(usuario => `
            <tr>
                <td>${usuario.id}</td>
                <td><strong>${escapeHtml(usuario.nome)}</strong></td>
                <td>${usuario.idade}</td>
                <td>${escapeHtml(usuario.cpf || "-")}</td>
                <td>${escapeHtml(usuario.email || "-")}</td>
                <td><button class="danger" type="button" data-delete="${usuario.id}">Excluir</button></td>
            </tr>
        `).join("") : '<tr><td colspan="6">Nenhum usuário cadastrado.</td></tr>';
    } catch (error) {
        mostrarMensagem(error.message, "error");
    }
}

async function carregarPautas() {
    try {
        const pautas = await api("/pautas");
        pautasContainer.innerHTML = pautas.length ? pautas.map(pauta => `
            <div class="pauta-item">
                <div>
                    <strong>${escapeHtml(pauta.nome)}</strong>
                    <p>${escapeHtml(pauta.descricao || "Sem descrição")}</p>
                </div>
                <div class="pauta-actions">
                    <span class="pauta-id">#${pauta.id}</span>
                    <button class="danger" type="button" data-delete-pauta="${pauta.id}">Excluir</button>
                </div>
            </div>
        `).join("") : '<p>Nenhuma pauta cadastrada.</p>';
    } catch (error) {
        mostrarMensagemPauta(error.message, "error");
    }
}

function escapeHtml(value) {
    const div = document.createElement("div");
    div.textContent = value;
    return div.innerHTML;
}

usuarioForm.addEventListener("submit", async event => {
    event.preventDefault();
    const form = new FormData(usuarioForm);
    const usuario = {
        nome: form.get("nome"),
        idade: Number(form.get("idade")),
        cpf: form.get("cpf") || null,
        email: form.get("email") || null,
        rua: form.get("rua") || null
    };
    const botao = usuarioForm.querySelector('button[type="submit"]');

    botao.disabled = true;
    botao.textContent = "Cadastrando...";
    try {
        await api("/usuarios", { method: "POST", body: JSON.stringify(usuario) });
        usuarioForm.reset();
        mostrarMensagemUsuario("Usuário cadastrado com sucesso.");
        await carregarUsuarios();
    } catch (error) {
        mostrarMensagemUsuario(error.message, "error");
    } finally {
        botao.disabled = false;
        botao.textContent = "Cadastrar usuário";
    }
});

pautaForm.addEventListener("submit", async event => {
    event.preventDefault();
    const form = new FormData(pautaForm);
    const pauta = {
        nome: String(form.get("nome") || "").trim(),
        descricao: String(form.get("descricao") || "").trim()
    };
    const botao = pautaForm.querySelector('button[type="submit"]');

    botao.disabled = true;
    botao.textContent = "Cadastrando...";
    try {
        await api("/pautas", { method: "POST", body: JSON.stringify(pauta) });
        pautaForm.reset();
        mostrarMensagemPauta("Pauta cadastrada com sucesso.");
        await carregarPautas();
    } catch (error) {
        mostrarMensagemPauta(error.message, "error");
    } finally {
        botao.disabled = false;
        botao.textContent = "Cadastrar pauta";
    }
});

usuariosTable.addEventListener("click", async event => {
    if (!(event.target instanceof Element)) return;
    const botao = event.target.closest("[data-delete]");
    const id = botao?.getAttribute("data-delete");
    if (!id) return;

    try {
        await api(`/usuarios/${id}`, { method: "DELETE" });
        mostrarMensagem("Usuário excluído.");
        await carregarUsuarios();
    } catch (error) {
        mostrarMensagem(error.message, "error");
    }
});

pautasContainer.addEventListener("click", async event => {
    if (!(event.target instanceof Element)) return;
    const botao = event.target.closest("[data-delete-pauta]");
    const id = botao?.getAttribute("data-delete-pauta");
    if (!id) return;

    try {
        await api(`/pautas/${id}`, { method: "DELETE" });
        mostrarMensagemPauta("Pauta excluída.");
        await carregarPautas();
    } catch (error) {
        mostrarMensagemPauta(error.message, "error");
    }
});

document.querySelector("#atualizar").addEventListener("click", () => {
    carregarUsuarios();
    carregarPautas();
    mostrarMensagem("Dados atualizados.");
});

carregarUsuarios();
carregarPautas();
