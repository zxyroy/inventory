import React, { Component } from 'react'
import { Table, Button } from 'reactstrap';
import ModalForm from '../Modals/Modal'

class DataTable extends Component {

	deleteItem = id => {
		let confirmDelete = window.confirm('Delete item forever?')
		if(confirmDelete){
			fetch('/api/inventory/'+id, {
				method: 'delete',
				headers: {
					'Content-Type': 'application/json'
				}
			})
				.then(response => response.status)
				.then(item => {
					this.props.deleteItemFromState(id)
				})
				.catch(err => console.log(err))
		}

	}

	render() {

		const items = this.props.items.map(item => {
			return (
				<tr key={item.id}>
					<th scope="row">{item.id}</th>
					<td>{item.name}</td>
					<td>{item.category}</td>
					<td>{item.subCategory}</td>
					<td>{item.quantity}</td>
					<td>
						<div style={{width:"110px"}}>
							<ModalForm buttonLabel="Edit" item={item} updateState={this.props.updateState}/>
							{' '}
							<Button color="danger" onClick={() => this.deleteItem(item.id)}>Del</Button>
						</div>
					</td>
				</tr>
			)
		})

		return (
			<Table responsive hover>
				<thead>
				<tr>
					<th>ID</th>
					<th>Name</th>
					<th>Category</th>
					<th>Sub Category</th>
					<th>Quantity</th>
					<th>Actions</th>
				</tr>
				</thead>
				<tbody>
				{items}
				</tbody>
			</Table>
		)
	}
}

export default DataTable