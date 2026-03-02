param($from, $to, $subject, $body, $smtpUser, $smtpPassword)
$argsMissing = $false
if ($from -eq $null) 
{
    Write-Host('Informe o remetente (From).')
    $argsMissing = $true
}
if ($to -eq $null) 
{
    Write-Host('Informe o destinatário (To).')
    $argsMissing = $true
}
if ($subject -eq $null) 
{
    Write-Host('Informe o assunto (Subject).')
    $argsMissing = $true
}
if ($body -eq $null) 
{
    Write-Host('Informe o texto da mensagem (Body).')
    $argsMissing = $true
}
if ($smtpUser -eq $null) 
{
    Write-Host('Informe o usuário NPI.')
    $argsMissing = $true
}
if ($smtpPassword -eq $null) 
{
    Write-Host('Informe a senha do usuário NPI.')
    $argsMissing = $true
}
if ($argsMissing -eq $false)
{
    $securePassword = ConvertTo-SecureString $smtpPassword -AsPlainText -Force
    $credential = New-Object System.Management.Automation.PSCredential ($smtpUser, $securePassword)

    $sendMailMessage = @{
        From = $from
        To = $to
        Subject = $subject
        Body = $body
        Encoding = 'utf8'
        SmtpServer = 'TMU-CS.MAIL.ALLIANZ'
        Port = '587'
        Credential = $credential
    
    }

    Send-MailMessage @sendMailMessage -UseSsl

    Write-Host('E-mail enviado com sucesso!')
}
else
{
    Write-Host('Revise os parâmetros.')
}