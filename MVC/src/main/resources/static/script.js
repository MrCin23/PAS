document.addEventListener('DOMContentLoaded', () => {
    const filterInput = document.getElementById('filter');
    let clientList = document.querySelector('#clients tbody');
    // console.log(clientList)

    filterInput.addEventListener('input', async () => {
        const query = filterInput.value.trim();
        if(query === '') {
            // console.log("aa")
            await fetch(`/client/find`)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Failed to fetch clients');
                    }

                    return response.json();
                })
                .then(clients => updateClientList(clients))
                .catch(error => console.error('Error fetching clients:', error));
        }
        else {
            // console.log("bb")
            await fetch(`/client/find/${encodeURIComponent(query)}`)
                .then(response => {
                    if (!response.ok) {
                        return 0;
                        //throw new Error('Failed to fetch clients');
                    } else {
                        return response.json();
                    }
                })
                .then(clients => updateClientList(clients))
                .catch(error => console.error('Error fetching clients:', error));
        }
    });

    function updateClientList(clients) {
        clientList.innerHTML = '';
        // clientList = ''
        console.log(clients)
        if (clients === 0) {
            const noResultRow = document.createElement('tr');
            const noResultCell = document.createElement('td');
            noResultCell.colSpan = 7;
            noResultCell.textContent = 'No clients found';
            noResultRow.appendChild(noResultCell);
            clientList.appendChild(noResultRow);
            return;
        }

        clients.forEach(client => {

            let row = document.createElement('tr');
            // console.log(client)
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
            // console.log(row)
            clientList.appendChild(row);
        });
    }
});