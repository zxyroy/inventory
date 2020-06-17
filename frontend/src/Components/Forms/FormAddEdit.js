import React from 'react';
import {Button, Form, FormGroup, Input, Label} from 'reactstrap';

class AddEditForm extends React.Component {
	state = {
		id: 0,
		name: '',
		category: '',
		categoryId: '',
		subCategory: '',
		subCategoryId: '',
		quantity: 0,
		categories: [],
		subCategories: []
	}

	onChange = e => {
		this.setState({[e.target.name]: e.target.value})
		if (e.target.name === 'categoryId')
			this.fetchSubCategory(e.target.value)
	}

	submitFormAdd = e => {
		e.preventDefault()
		fetch('/api/inventory', {
			method: 'post',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({
				name: this.state.name,
				categoryId: this.state.categoryId,
				subCategoryId: this.state.subCategoryId,
				quantity: this.state.quantity
			})
		})
			.then(response => response.json())
			.then(item => {
				if (item.id) {
					this.props.addItemToState(item)
					this.props.toggle()
				} else {
					console.log('failure')
				}
			})
			.catch(err => console.log(err))
	}

	submitFormEdit = e => {
		e.preventDefault()
		fetch('/api/inventory/' + this.state.id + '?quantity=' + this.state.quantity, {
			method: 'put',
			headers: {
				'Content-Type': 'application/json'
			}
		})
			.then(response => response.json())
			.then(item => {
				if (item.id) {
					this.props.updateState(item)
					this.props.toggle()
				} else {
					console.log('failure')
				}
			})
			.catch(err => console.log(err))
	}

	componentDidMount() {
		// if item exists, populate the state with proper data
		if (this.props.item) {
			const {id, name, category, subCategory, quantity} = this.props.item
			this.setState({id, name, category, subCategory, quantity})
		} else {
			fetch('/api/categories/')
				.then(response => response.json())
				.then(items => {
					this.setState({categories: items})
				})
				.catch(err => console.log(err))
		}
	}

	fetchSubCategory(id) {
		fetch('/api/categories/' + id)
			.then(response => response.json())
			.then(items => {
				this.setState({subCategories: items})
			})
			.catch(err => console.log(err))
	}

	render() {
		let categoryLabel = ''
		let categoryInput = ''
		if (this.state.category === '') {
			categoryLabel = <Label for="categoryId">Category</Label>
			categoryInput = <Input type="select" name="categoryId" id="categoryId" onChange={this.onChange}
			                           value={this.state.categoryId === null ? '' : this.state.categoryId}>
				{this.state.categories.map((category) =>
					<option value={category.id}
					        selected={this.state.categoryId === category.id}>{category.name}</option>
				)}
			</Input>
			if (this.state.categoryId === '' && this.state.categories.length > 0) {
				this.setState({categoryId: this.state.categories[0].id})
				this.fetchSubCategory(this.state.categories[0].id)
			}
		} else {
			categoryLabel = <Label for="category">Category</Label>
			categoryInput =
				<Input type="text" name="category" id="category" readonly={this.props.item}
				       value={this.state.category}/>
		}

		let subCategoryLabel = ''
		let subCategoryInput = ''
		if (this.state.subCategory === '') {
			subCategoryLabel = <Label for="subCategoryId">Sub Category</Label>;
			subCategoryInput = <Input type="select" name="subCategoryId" id="subCategoryId" onChange={this.onChange}
			                              value={this.state.subCategoryId === null ? '' : this.state.subCategoryId}>
				{this.state.subCategories.map((category) =>
					<option value={category.id}
					        selected={this.state.categoryId === category.id}>{category.name}</option>
				)}
			</Input>
			if (this.state.subCategoryId === '' && this.state.subCategories.length > 0) {
				this.setState({subCategoryId: this.state.subCategories[0].id})
			}
		} else {
			subCategoryLabel = <Label for="subCategory">Sub Category</Label>;
			subCategoryInput =
				<Input type="text" name="subCategory" id="subCategory" readonly={this.props.item}
				       value={this.state.subCategory}/>
		}

		return (
			<Form onSubmit={this.props.item ? this.submitFormEdit : this.submitFormAdd}>
				<FormGroup>
					<Label for="first">Name</Label>
					<Input type="text" name="name" id="name" invalid={this.state.name.length === 0} readOnly={this.props.item}
					       onChange={this.onChange} value={this.state.name}/>
				</FormGroup>
				<FormGroup>
					{categoryLabel}
					{categoryInput}
				</FormGroup>
				<FormGroup>
					{subCategoryLabel}
					{subCategoryInput}
				</FormGroup>
				<FormGroup>
					<Label for="quantity">Quantity</Label>
					<Input type="number" min='0' step='1' name="quantity" id="quantity" onChange={this.onChange}
					       value={this.state.quantity === null ? '' : this.state.quantity}/>
				</FormGroup>
				<Button disabled={this.state.name.length === 0}>Submit</Button>
			</Form>
		);
	}
}

export default AddEditForm