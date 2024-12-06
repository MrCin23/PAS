document.addEventListener('DOMContentLoaded', () => {
    const filterInput = document.getElementById('filter');
    const clientList = document.getElementById('clients');

    filterInput.addEventListener('input', () => {
        const query = filterInput.value.trim(); // Pobierz wartość z pola filtrowania
        fetch(`/client/find/${encodeURIComponent(query)}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to fetch clients');
                }
                return response.json();
            })
            .then(clients => updateClientList(clients))
            .catch(error => console.error('Error fetching clients:', error));
    });

    function updateClientList(clients) {
        clientList.innerHTML = ''; // Wyczyść istniejącą tabelę

        if (clients.length === 0) {
            const noResultRow = document.createElement('tr');
            const noResultCell = document.createElement('td');
            noResultCell.colSpan = 7;
            noResultCell.textContent = 'No clients found';
            noResultRow.appendChild(noResultCell);
            clientList.appendChild(noResultRow);
            return;
        }

        clients.forEach(client => {
            const row = document.createElement('tr');
            console.log(client)
            row.innerHTML = `
                <td>${client.entityId.uuid}</td>
                <td>${client.username}</td>
                <td>${client.emailAddress}</td>
                <td>${client.clientType.name}</td>
                <td>${client.role}</td>
                <td>${client.active}</td>
                <td>
                    <form action="/client/${client.entityId.uuid}" method="GET">
                        <button type="submit">More Info</button>
                    </form>
                </td>
            `;

            clientList.appendChild(row);
        });
    }
});
