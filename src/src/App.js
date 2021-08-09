import './App.css';
import React,{Component} from 'react';
import {getAllStudents} from './client' 
import {Container} from './Container';
import {
Table,Avatar,Spin,Modal, Empty
} from 'antd';
import { LoadingOutlined } from '@ant-design/icons';
import Footer from './Footer';
import AddStudentForm from './forms/AddStudentForm'
import {errorNotification} from './Notification';

const getIndicatorIcon=()=><LoadingOutlined type="loading" style={{fontSize:24}}/>


class App extends Component {

  state={
    students:[],
    isFetching:false,
    isAddStudentModalVisible:false
  }

  componentDidMount(){
    this.fetchStudents();
  }

  openAddStudentModal=()=>this.setState({isAddStudentModalVisible:true})

  
  closeAddStudentModal=()=>this.setState({isAddStudentModalVisible:false})

  fetchStudents=()=>{
    this.setState({
      isFetching:true
    })
    getAllStudents()
    .then( r => r.json() )
    .then( students =>{
    
      this.setState({
        students,
        isFetching:false
        })
    } 
        
    ).catch(error=>{
     console.log(error.error);
      const message=error.error.message;
      const description=error.error.error;
       errorNotification(message,description);
      this.setState({
        isFetching:false
      }); 
    })
    
}

render(){

  const {students,isFetching,isAddStudentModalVisible} = this.state;
  
  if(isFetching)
  {
    return (
      <Container>
        <Spin indicator={getIndicatorIcon()}/>
      </Container> 
    )
  }

  const commonElements=()=>(<div>
    <Modal title="Add new Student"
      visible={isAddStudentModalVisible}
      onOk={this.closeAddStudentModal}
      onCancel={this.closeAddStudentModal}
      width={1000}>
      <AddStudentForm onSuccess={()=>{this.closeAddStudentModal();
        this.fetchStudents();
      }}
      onFailure={(error)=>{
        const message=error.error.message;
      const description=error.error.httpStatus;
       errorNotification(message,description);
      
      }}
      />
      </Modal>
      <Footer numberOfStudents={students.length}
      handleAddStudentClickEvent={this.openAddStudentModal}
      />
  </div>)
  if(students && students.length>0)
  {
    
      const columns=[
        {
          title:" ",
          key:'avatar',
          render: (text,student) => (
            <Avatar size='large'>
              {`${student.firstName.charAt(0).toUpperCase()}${student.lastName.charAt(0).toUpperCase()}`}
            </Avatar>
          )
        },
        {
          title:'StudentId',
          dataIndex:'studentId',
          key:'studentId'      
        },
        {
          title:'First Name',
          dataIndex:'firstName',
          key:'firstName'      
        },{
          title:'Last Name',
          dataIndex:'lastName',
          key:'lastName'      
        },
        {
          title:'Email',
          dataIndex:'email',
          key:'email'      
        },
        {
          title:'Gender',
          dataIndex:'gender',
          key:'gender'      
        },
      ];

      return <Container>
        <Table style={{marginBottom:'100px'}} dataSource={students} columns={columns} pagination={false} rowKey='studentId'/>
        <span style={{color:"white"}}>_</span>
        {commonElements()}
      </Container>
  }
  
    return (<Container>
      <Empty description={
      <h1>No Students found</h1>}/>
      {commonElements()}
      </Container>);
  
  
}
  
}

export default App;
