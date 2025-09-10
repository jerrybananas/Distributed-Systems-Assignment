# Pet Registry System - User Manual

## Table of Contents
1. [System Overview](#system-overview)
2. [Getting Started](#getting-started)
3. [User Roles and Access](#user-roles-and-access)
4. [Login Process](#login-process)
5. [Citizen User Guide](#citizen-user-guide)
6. [Veterinarian User Guide](#veterinarian-user-guide)
7. [Administrator User Guide](#administrator-user-guide)

---

## System Overview

The Pet Registry System is a comprehensive web-based application designed to manage pet information, medical records, and user accounts. The system serves three distinct user types: Citizens (pet owners), Veterinarians, and Administrators, each with specific access levels and functionalities.

### Key Features
- **Pet Registration**: Citizens can register their pets with complete information
- **Medical Record Management**: Veterinarians can update and approve pet medical records
- **User Management**: Administrators can create and manage system users
- **Role-Based Security**: Different access levels based on user roles
- **Modern Web Interface**: Responsive design that works on all devices

---

## Getting Started

### Prerequisites
1. Web browser (Chrome, Firefox, Safari, or Edge)
2. Internet connection
3. Valid user credentials (provided by system administrator)

### First Time Setup
1. Ensure the system is running and accessible
2. Navigate to the application URL: `http://localhost:8080/login`
3. Use the credentials provided by your system administrator
4. Change your password if required by your organization's policy

---

## User Roles and Access

### Citizen (Pet Owner)
- **Purpose**: Register and manage personal pets
- **Access Level**: Limited to own pet records
- **Key Functions**: 
  - Register new pets
  - View owned pets
  - Track veterinary approval status

### Veterinarian
- **Purpose**: Review and approve pet medical records
- **Access Level**: View all pets, edit medical records
- **Key Functions**:
  - Search pets by ID
  - Update medical histories
  - Approve pet records
  - Edit pet information

### Administrator
- **Purpose**: System management and user administration
- **Access Level**: Full system access
- **Key Functions**:
  - Create new users
  - Modify user accounts
  - Delete users
  - View system statistics

---

## Login Process

### Accessing the System
1. Open your web browser
2. Navigate to: `http://localhost:8080/login`
3. Enter your username and password
4. Click "Sign In"

### Default Credentials (Development)
- **Admin**: Username: `admin`, Password: `pass`
- **Citizen**: Username: `citizen`, Password: `pass`
- **Veterinarian**: Username: `vet`, Password: `pass`

> **Note**: These are development credentials. In production, use secure credentials provided by your administrator.

### After Login
- The system automatically redirects you to your role-specific dashboard
- Session expires after 5 minutes of inactivity for security

---

## Citizen User Guide

### Dashboard Overview
When you log in as a citizen, you'll see the Pet Owner Dashboard with:
- Welcome message with your name
- "Register New Pet" button
- "My Pets" section showing all your registered pets
- System statistics and information

### Registering a New Pet

#### Step 1: Access Registration Form
1. Click "Register New Pet" from your dashboard
2. You'll be redirected to the pet registration form

#### Step 2: Fill Pet Information
1. **Pet Name**: Enter your pet's name (required, max 100 characters)
2. **Species**: Select from available options:
   - Dog
   - Cat
   - Hamster
   - Bird
   - Fish
   - Reptile
   - Other (specify custom species)
3. **Gender**: Choose Male or Female
4. **Birthday**: Select your pet's birth date using the date picker

#### Step 3: Submit Registration
1. Review all information for accuracy
2. Click "Register Pet"
3. Wait for confirmation message
4. You'll be redirected back to your dashboard

#### Important Notes
- Pet ID numbers are automatically assigned by the system
- All fields are required for registration
- Medical history is initially empty and will be updated by veterinarians

### Viewing Your Pets

#### Accessing Pet List
1. From your dashboard, the "My Pets" section displays automatically
2. Click "Load My Pets" if the list doesn't appear immediately

#### Pet Information Display
Each pet record shows:
- **Pet ID**: Unique 9-digit identifier (e.g., 000000123)
- **Name**: Pet's name with tag icon
- **Species**: Type of animal
- **Gender**: Male (♂) or Female (♀) with color-coded icons
- **Birthday**: Birth date with calendar icon
- **Medical History**: Current medical records or "No records yet"
- **Vet Approval**: ✓ (approved) or ✗ (pending approval)

#### Understanding Approval Status
- **Green Check (✓)**: Pet record has been approved by a veterinarian
- **Red X (✗)**: Pet record is pending veterinary approval
- Approval is required for official recognition of pet records

### Navigation
- Use "Logout" button to end your session securely
- Return to dashboard using navigation buttons
- All pages are mobile-responsive for use on any device

---

## Veterinarian User Guide

### Dashboard Overview
The Veterinarian Dashboard provides:
- Professional greeting with your credentials
- Pet search functionality
- Medical record management tools
- Administrative options

### Searching for Pets

#### Pet ID Search
1. Enter the Pet ID in the search box
2. Accepted formats:
   - Simple numbers: `123`
   - Padded format: `000000123`
   - Formatted: `000-000-123`
3. Click "Search Pet" or press Enter
4. System will automatically normalize the ID format

#### Search Results
When a pet is found, you'll see:
- Complete pet information table
- Medical history section
- Approval status indicator
- Action buttons for editing and approval

### Managing Medical Records

#### Viewing Medical History
- Medical records appear in a dedicated section below pet details
- Shows current medical history or "No medical history records available"
- Approval status is clearly indicated with color-coded badges

#### Updating Medical Records
1. Click "Edit Pet" to enter edit mode
2. Medical history section becomes editable with a text area
3. Add or modify medical information as needed
4. Click "Save Changes" to update the record
5. Click "Cancel" to discard changes

#### Approving Pet Records
1. Locate the "Approve Record" button (only visible for unapproved pets)
2. Click the button to approve the pet record
3. System will confirm approval and update the status
4. Button disappears after successful approval

### Editing Pet Information

#### Available Fields for Editing
- **Pet Name**: Update the pet's name
- **Species**: Change the species/breed information
- **Gender**: Modify gender if needed
- **Medical History**: Add or update medical records

#### Edit Process
1. Click "Edit Pet" to enter edit mode
2. Editable fields are highlighted with yellow background
3. Make necessary changes
4. Click "Save Changes" to apply updates
5. System provides confirmation of successful updates

#### Restrictions
- Pet ID and Owner ID cannot be modified by veterinarians
- Birthday field is read-only for data integrity
- Only authorized veterinarians can approve records

---

## Administrator User Guide

### Dashboard Overview
The Administrator Dashboard includes:
- System overview with statistics
- Total pets and users count
- User management functions
- Administrative tools

### User Management

#### Creating New Users

##### Step 1: Access Registration Form
1. Click "Register User" from the admin dashboard
2. You'll be directed to the user registration form

##### Step 2: User Information
1. **Username**: Enter unique username (max 50 characters)
2. **Password**: Create secure password (minimum 6 characters)
3. **Confirm Password**: Re-enter password for verification
4. **User Role**: Select either:
   - **Citizen**: For pet owners
   - **Veterinarian**: For medical professionals

##### Step 3: Submit Registration
1. Review information for accuracy
2. Click "Register User"
3. System automatically assigns unique User ID
4. Confirmation message appears upon success

#### User ID Assignment
- User IDs follow the format: [role_prefix][6_digits]
- **1xxxxxx**: Admin users
- **2xxxxxx**: Citizen users  
- **3xxxxxx**: Veterinarian users
- IDs are automatically generated for security and consistency

#### Modifying User Accounts
1. Click "Modify User" from admin dashboard
2. Select user to modify from the system
3. Update required information
4. Save changes to apply modifications

#### Deleting User Accounts
1. Click "Delete User" from admin dashboard
2. Select user to remove
3. Confirm deletion action
4. User account and access will be permanently removed

### System Monitoring

#### Viewing Statistics
The admin dashboard displays real-time system information:
- **Total Pets**: Current number of registered pets in the system
- **Total Users**: Current number of active user accounts
- Statistics update automatically when accessing the dashboard

#### System Health
- Monitor user activity and system usage
- Track pet registration trends
- Manage system capacity and performance

### Security Management

#### Password Policies
- Minimum password length: 6 characters
- Passwords stored securely (encrypted in production)
- Regular password updates recommended

#### Access Control
- Role-based permissions strictly enforced
- Session timeout: 5 minutes for security
- Automatic logout after inactivity

---

*This manual covers Pet Registry System version 1.0. For the most current information and updates, contact your system administrator.*

**Last Updated**: January 2025
**Manual Version**: 1.0
**System Version**: 1.0
